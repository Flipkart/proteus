package com.flipkart.layoutengine.builder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.flipkart.layoutengine.ParserContext;
import com.flipkart.layoutengine.parser.LayoutHandler;
import com.flipkart.layoutengine.toolbox.BitmapLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A layout builder which can parse json to construct an android view out of it. It uses the registered handlers to convert the json string to a view and then assign attributes.
 */
public class SimpleLayoutBuilder implements LayoutBuilder {

    protected static final String TAG = SimpleLayoutBuilder.class.getSimpleName();

    public static final String TYPE = "type";
    public static final String CHILDREN = "children";
    public static final String CHILD_TYPE = "childType";

    private HashMap<String,LayoutHandler> layoutHandlers = new HashMap<String, LayoutHandler>();
    private LayoutBuilderCallback listener;
    private BitmapLoader bitmapLoader;

    // see the getter for doc
    private boolean isSynchronousRendering = false;


    /**
     * Package private constructor so that no client can access it without the factory class
     */
    SimpleLayoutBuilder() {}

    /**
     * Registers a {@link LayoutHandler} for the specified view type. All the attributes will pass through {@link LayoutHandler#handleAttribute(com.flipkart.layoutengine.ParserContext, String, com.google.gson.JsonObject, com.google.gson.JsonElement, Object)} and expect to be handled.
     * @param viewType The string value for "view" attribute.
     * @param handler The handler which should handle this view.
     */
    @Override
    public void registerHandler(String viewType, LayoutHandler handler)
    {
        handler.prepare(context);
        layoutHandlers.put(viewType, handler);

    }

    /**
     * Unregisters the specified view type.
     * @param viewType The string value for "view" attribute.
     */
    @Override
    public void unregisterHandler(String viewType)
    {
        layoutHandlers.remove(viewType);
    }

    /**
     * Unregisters all handlers.
     */
    @Override
    public void unregisterAllHandlers()
    {
        layoutHandlers.clear();
    }

    /**
     * Get the handler registered with the supplied view type
     * @param viewType
     * @return
     */
    @Override
    public LayoutHandler getHandler(String viewType)
    {
        return layoutHandlers.get(viewType);
    }

    private Context context;

    public SimpleLayoutBuilder(Context context) {
        this.context = context;
    }

    @Override
    public View build(ViewGroup parent, JsonObject jsonObject)
    {
        return buildImpl(createParserContext(), parent, jsonObject, null , 0);
    }


    protected ParserContext createParserContext()
    {
        ParserContext parserContext = new ParserContext();
        parserContext.setLayoutBuilder(this);
        return parserContext;
    }

    /**
     * Starts recursively parsing the given jsonObject.
     * @param context Represents the context of the parsing.
     * @param parent The parent view group under which the view being created has to be added as a child.
     * @param jsonObject The jsonObject which represents the current node which is getting parsed.
     * @param existingView A view which needs to be used instead of creating a new one. Pass null for first pass.
     * @param childIndex index of child inside its parent view
     * @return
     */
    protected View buildImpl(final ParserContext context, final ViewGroup parent, final JsonObject jsonObject, View existingView , final int childIndex)
    {
        JsonElement viewTypeElement = jsonObject.get(TYPE);
        //System.out.println("ViewType "+ viewTypeElement.getAsString());
        String viewType = null;
        if(viewTypeElement!=null) {
            viewType = viewTypeElement.getAsString();
        }
        else
        {

            Log.e(TAG,"view cannot be null");
            return null;
        }

        LayoutHandler<View> handler = layoutHandlers.get(viewType);
        if(handler == null)
        {
            return onUnknownViewEncountered(context,viewType,parent,jsonObject,childIndex);
        }



        JsonElement childViewElement = jsonObject.get(CHILD_TYPE);
        JsonElement childrenElement = jsonObject.get(CHILDREN);
        JsonArray children = null;
        if(childrenElement!=null) {
            children = parseChildren(handler,context,childrenElement,childIndex);
        }

        /**
         * View creation.
         */
        final View self;
        if(existingView == null) {
            self = createView(context, parent, handler, jsonObject);
            handler.setupView(parent,self);
        }
        else
        {
            self = existingView;
        }

        /**
         * Parsing each attribute and setting it on the view.
         */
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {

            if(TYPE.equals(entry.getKey()) || CHILDREN.equals(entry.getKey()) || CHILD_TYPE.equals(entry.getKey()))
            {
                continue;
            }
            boolean handled = handleAttribute(handler,context,entry.getKey(),jsonObject,entry.getValue(),self,parent, childIndex);

            if(!handled)
            {
                onUnknownAttributeEncountered(context, entry.getKey(), entry.getValue(), jsonObject, self , childIndex);
            }
        }

        /**
         * Processing the children.
         */

        if(children!=null && children.size()>0) {
            ViewGroup selfViewGroup = (ViewGroup) self;
            List<View> childrenToAdd = new ArrayList<View>();
            for (int i = 0; i < children.size(); i++) {
                JsonObject childObject = children.get(i).getAsJsonObject();
                if(childViewElement!=null)
                {
                    // propagate the value of 'childView' to the recursive calls
                    childObject.add(TYPE,childViewElement);
                }
                View childView = buildImpl(context, selfViewGroup, childObject, null, i);
                if(childView!=null) {
                    childrenToAdd.add(childView);
                }

            }
            if(childrenToAdd.size()>0) {
                handler.addChildren(this.context, selfViewGroup, childrenToAdd);
            }
        }

        return self;

    }

    protected JsonArray parseChildren(LayoutHandler handler, ParserContext context, JsonElement childrenElement, int childIndex)
    {
        return handler.parseChildren(context,childrenElement,childIndex);
    }

    protected boolean handleAttribute(LayoutHandler handler, ParserContext context, String attribute, JsonObject jsonObject, JsonElement element, View view, ViewGroup parent, int index)
    {
        return handler.handleAttribute(context, attribute, jsonObject , element, view, index);
    }

    protected void onUnknownAttributeEncountered(ParserContext context, String attribute, JsonElement element, JsonObject object, View view , int childIndex)
    {
        if(listener!=null)
        {
            listener.onUnknownAttribute(context,attribute,element, object, view, childIndex);
        }
    }

    protected View onUnknownViewEncountered(ParserContext context, String viewType, ViewGroup parent, JsonObject jsonObject, int childIndex) {

        if(listener!=null)
        {
            return listener.onUnknownViewType(context,viewType,jsonObject,parent,childIndex);
        }
        return null;
    }



    protected View createView(ParserContext context, ViewGroup parent, LayoutHandler<View> handler, JsonObject object)
    {
        View view = handler.createView(context, this.context, parent, object);
        return view;
    }




    @Override
    public LayoutBuilderCallback getListener() {
        return listener;
    }

    @Override
    public void setListener(LayoutBuilderCallback listener) {
        this.listener = listener;
    }

    @Override
    public BitmapLoader getNetworkDrawableHelper(){
        return bitmapLoader;
    }

    /**
     * All network bitmap calls will be handed over to this loader.
     * @param bitmapLoader
     */
    @Override
    public void setBitmapLoader(BitmapLoader bitmapLoader){
        this.bitmapLoader = bitmapLoader;
    }

    /**
     *
     * Set this to true for rendering preview immediately. This is to be used to decide whether remote resources like remote images are to be downloaded synchronously or not
     * @return true if the all views should be rendered immediately.
     *
     */
    @Override
    public boolean isSynchronousRendering() {
        return isSynchronousRendering;
    }

    @Override
    public void setSynchronousRendering(boolean isSynchronousRendering) {
        this.isSynchronousRendering = isSynchronousRendering;
    }
}
