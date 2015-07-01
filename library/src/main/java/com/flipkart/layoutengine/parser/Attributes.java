package com.flipkart.layoutengine.parser;

/**
 * @author kirankumar
 * @author Aditya Sharat {@literal <aditya.sharat@flipkart.com>}
 */
public class Attributes {

    public static class View {

        /**
         * @serialField weight
         */
        public static Attribute Weight = new Attribute("layout_weight");

        /**
         * @serialField width
         */
        public static Attribute Width = new Attribute("layout_width");

        /**
         * @serialField background
         */
        public static Attribute Background = new Attribute("background");

        /**
         * @serialField height
         */
        public static Attribute Height = new Attribute("layout_height");
        public static Attribute LayoutGravity = new Attribute("layout_gravity");
        public static Attribute Gravity = new Attribute("gravity");
        public static Attribute Padding = new Attribute("padding");
        public static Attribute PaddingLeft = new Attribute("paddingLeft");
        public static Attribute PaddingTop = new Attribute("paddingTop");
        public static Attribute PaddingRight = new Attribute("paddingRight");
        public static Attribute PaddingBottom = new Attribute("paddingBottom");
        public static Attribute Margin = new Attribute("layout_margin");
        public static Attribute MarginLeft = new Attribute("layout_marginLeft");
        public static Attribute MarginTop = new Attribute("layout_marginTop");
        public static Attribute MarginRight = new Attribute("layout_marginRight");
        public static Attribute MarginBottom = new Attribute("layout_marginBottom");
        public static Attribute Alpha = new Attribute("alpha");
        public static Attribute Visibility = new Attribute("visibility");
        public static Attribute Id = new Attribute("id");
        public static Attribute Tag = new Attribute("tag");
        public static Attribute Above = new Attribute("layout_above");
        public static Attribute AlignBaseline = new Attribute("layout_alignBaseline");
        public static Attribute AlignBottom = new Attribute("layout_alignBottom");
        public static Attribute AlignEnd = new Attribute("layout_alignEnd");
        public static Attribute AlignLeft = new Attribute("layout_alignLeft");
        public static Attribute AlignRight = new Attribute("layout_alignRight");
        public static Attribute AlignStart = new Attribute("layout_alignStart");
        public static Attribute AlignTop = new Attribute("layout_alignTop");
        public static Attribute Below = new Attribute("layout_below");
        public static Attribute ToEndOf = new Attribute("layout_toEndOf");
        public static Attribute ToLeftOf = new Attribute("layout_toLeftOf");
        public static Attribute ToRightOf = new Attribute("layout_toRightOf");
        public static Attribute ToStartOf = new Attribute("layout_toStartOf");
        public static Attribute AlignParentBottom = new Attribute("layout_alignParentBottom");
        public static Attribute AlignParentEnd = new Attribute("layout_alignParentEnd");
        public static Attribute AlignParentLeft = new Attribute("layout_alignParentLeft");
        public static Attribute AlignParentRight = new Attribute("layout_alignParentRight");
        public static Attribute AlignParentStart = new Attribute("layout_alignParentStart");
        public static Attribute AlignParentTop = new Attribute("layout_alignParentTop");
        public static Attribute CenterHorizontal = new Attribute("layout_centerHorizontal");
        public static Attribute CenterInParent = new Attribute("layout_centerInParent");
        public static Attribute CenterVertical = new Attribute("layout_centerVertical");
        public static Attribute ContentDescription = new Attribute("contentDescription");
        public static Attribute Clickable = new Attribute("clickable");
        public static Attribute OnClick = new Attribute("onClick");

    }

    public static class Webview {
        public static Attribute Url = new Attribute("url");

    }

    public static class RatingBar {
        public static Attribute NumStars = new Attribute("numStars");
        public static Attribute Rating = new Attribute("rating");
        public static Attribute IsIndicator = new Attribute("isIndicator");
        public static Attribute StepSize = new Attribute("stepSize");
        public static Attribute ProgressDrawable = new Attribute("progressDrawable");
        public static Attribute MinHeight = new Attribute("minHeight");
        public static Attribute MaxHeight = new Attribute("maxHeight");
    }

    public static class TextView {
        public static Attribute Gravity = new Attribute("gravity");
        public static Attribute Text = new Attribute("text");
        public static Attribute TextSize = new Attribute("textSize");
        public static Attribute TextColor = new Attribute("textColor");
        public static Attribute DrawableLeft = new Attribute("drawableLeft");
        public static Attribute DrawableRight = new Attribute("drawableRight");
        public static Attribute DrawableTop = new Attribute("drawableTop");
        public static Attribute DrawableBottom = new Attribute("drawableBottom");
        public static Attribute DrawablePadding = new Attribute("drawablePadding");
        public static Attribute MaxLines = new Attribute("maxLines");
        public static Attribute Ellipsize = new Attribute("ellipsize");
        public static Attribute PaintFlags = new Attribute("paintFlags");
        public static Attribute Prefix = new Attribute("prefix");
        public static Attribute Suffix = new Attribute("suffix");
        public static Attribute TextStyle = new Attribute("textStyle");
        public static Attribute SingleLine = new Attribute("singleLine");
        public static Attribute TextAllCaps = new Attribute("textAllCaps");

    }

    public static class CheckBox {
        public static Attribute Checked = new Attribute("checked");
        public static Attribute Button = new Attribute("button");
    }

    public static class FrameLayout {
        public static Attribute Gravity = new Attribute("gravity");
        public static Attribute HeightRatio = new Attribute("heightRatio");
        public static Attribute WidthRatio = new Attribute("widthRatio");
    }

    public static class ImageView {
        public static Attribute Src = new Attribute("src");
        public static Attribute ScaleType = new Attribute("scaleType");
        public static Attribute AdjustViewBounds = new Attribute("adjustViewBounds");
    }

    public static class LinearLayout {
        public static Attribute Orientation = new Attribute("orientation");
        public static Attribute Divider = new Attribute("divider");
        public static Attribute DividerPadding = new Attribute("dividerPadding");
        public static Attribute ShowDividers = new Attribute("showDividers");
    }

    public static class ListView {
        /**
         * XML attributes inherited from {@link android.widget.AbsListView}
         */
        public static Attribute CacheColorHint = new Attribute("cacheColorHint");
        public static Attribute ChoiceMode = new Attribute("choiceMode");
        public static Attribute DrawSelectorOnTop = new Attribute("drawSelectorOnTop");
        public static Attribute FastScrollEnabled = new Attribute("fastScrollEnabled");
        public static Attribute ListSelector = new Attribute("listSelector");
        public static Attribute ScrollingCache = new Attribute("scrollingCache");
        public static Attribute SmoothScrollbar = new Attribute("smoothScrollbar");
        public static Attribute StackFromBottom = new Attribute("stackFromBottom");
        public static Attribute TextFilterEnabled = new Attribute("textFilterEnabled");
        public static Attribute TranscriptMode = new Attribute("transcriptMode");
        /**
         * XML attributes of {@link android.widget.ListView}
         */
        public static Attribute Divider = new Attribute("divider");
        public static Attribute DividerHeight = new Attribute("dividerHeight");
        public static Attribute Entries = new Attribute("entries");
        public static Attribute FooterDividersEnabled = new Attribute("footerDividersEnabled");
        public static Attribute HeaderDividersEnabled = new Attribute("headerDividersEnabled");

        /**
         * XML attribute for the {@link android.widget.ListView} Adapter layout and data
         */
        public static Attribute ListViewData = new Attribute("listViewData");

    }

    public static class NetworkImageView {
        public static Attribute ImageUrl = new Attribute("imageUrl");
    }

    public static class HorizontalScrollView {
        public static Attribute FillViewPort = new Attribute("fillViewPort");
    }

    public static class ProgressBar {
        public static Attribute Progress = new Attribute("progress");
        public static Attribute Max = new Attribute("max");
        public static Attribute ProgressTint = new Attribute("progressTint");

    }

    public static class Attribute {
        private String name;

        public Attribute(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}