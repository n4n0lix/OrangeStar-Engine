package de.orangestar.engine.values;

/**
 * Describes a anchor point.
 * 
 * @author Oliver, Basti
 */
public enum Anchor {
    FRONT_TOP_LEFT, FRONT_TOP,    FRONT_TOP_RIGHT,
    FRONT_MID_LEFT, FRONT_MID,    FRONT_MID_RIGHT,
    FRONT_BOT_LEFT, FRONT_BOTTOM, FRONT_BOT_RIGHT,
    
    TOP_LEFT, TOP,    TOP_RIGHT,
    MID_LEFT, MID,    MID_RIGHT,
    BOT_LEFT, BOTTOM, BOT_RIGHT,
    
    BACK_TOP_LEFT, BACK_TOP,    BACK_TOP_RIGHT,
    BACK_MID_LEFT, BACK_MID,    BACK_MID_RIGHT,
    BACK_BOT_LEFT, BACK_BOTTOM, BACK_BOT_RIGHT;
    
    /**
     * If the anchor is left.
     * @return If the anchor is left
     */
    public final boolean isLeft() {
        return this == FRONT_TOP_LEFT || this == FRONT_MID_LEFT || this == FRONT_BOT_LEFT
            || this == TOP_LEFT       || this == MID_LEFT       || this == BOT_LEFT
            || this == BACK_TOP_LEFT  || this == BACK_MID_LEFT  || this == BACK_BOT_LEFT;
    }
    
    /**
     * If the anchor is in the horizontally center.
     * @return If the anchor is in the horizontally center
     */
    public final boolean isXAxisCenter() {
        return this == FRONT_TOP || this == FRONT_MID || this == FRONT_BOTTOM
            || this == TOP       || this == MID       || this == BOTTOM
            || this == BACK_TOP  || this == BACK_MID  || this == BACK_BOTTOM;
    }
    
    /**
     * If the anchor is right.
     * @return If the anchor is right
     */
    public final boolean isRight() {
        return this == FRONT_TOP_RIGHT || this == FRONT_MID_RIGHT || this == FRONT_BOT_RIGHT
            || this == TOP_RIGHT       || this == MID_RIGHT       || this == BOT_RIGHT
            || this == BACK_TOP_RIGHT  || this == BACK_MID_RIGHT  || this == BACK_BOT_RIGHT;
    }
    
    /**
     * If the anchor is top.
     * @return If the anchor is top
     */
    public final boolean isTop() {
        return this == FRONT_TOP_LEFT || this == FRONT_TOP || this == FRONT_TOP_RIGHT
            || this == TOP_LEFT       || this == TOP       || this == TOP_RIGHT
            || this == BACK_TOP_LEFT  || this == BACK_TOP  || this == BACK_TOP_RIGHT;
    }
    
    /**
     * If the anchor is in the vertically center.
     * @return If the anchor is in the vertically center
     */
    public final boolean isYAxisCenter() {
        return this == FRONT_MID_LEFT || this == FRONT_MID || this == FRONT_MID_RIGHT
            || this == MID_LEFT       || this == MID       || this == MID_RIGHT
            || this == BACK_MID_LEFT  || this == BACK_MID  || this == BACK_MID_RIGHT;
    }
    
    /**
     * If the anchor is bottom.
     * @return If the anchor is bottom
     */
    public final boolean isBottom() {
        return this == FRONT_BOT_LEFT || this == FRONT_BOTTOM || this == FRONT_BOT_RIGHT
            || this == BOT_LEFT       || this == BOTTOM       || this == BOT_RIGHT
            || this == BACK_BOT_LEFT  || this == BACK_BOTTOM  || this == BACK_BOT_RIGHT;
    }
    
    /**
     * If the anchor is top.
     * @return If the anchor is top
     */
    public final boolean isFront() {
        return this == FRONT_TOP_LEFT || this == FRONT_TOP     || this == FRONT_TOP_RIGHT
            || this == FRONT_MID_LEFT || this == FRONT_MID     || this == FRONT_MID_RIGHT
            || this == FRONT_BOT_LEFT || this == FRONT_BOTTOM  || this == FRONT_BOT_RIGHT;
    }
    
    /**
     * If the anchor is in the depth center.
     * @return If the anchor is in the vertically center
     */
    public final boolean isZAxisCenter() {
        return this == TOP_LEFT || this == TOP    || this == TOP_RIGHT
            || this == MID_LEFT || this == MID    || this == MID_RIGHT
            || this == BOT_LEFT || this == BOTTOM || this == BOT_RIGHT;
    }
    
    /**
     * If the anchor is top.
     * @return If the anchor is top
     */
    public final boolean isBack() {
        return this == BACK_TOP_LEFT || this == BACK_TOP     || this == BACK_TOP_RIGHT
            || this == BACK_MID_LEFT || this == BACK_MID     || this == BACK_MID_RIGHT
            || this == BACK_BOT_LEFT || this == BACK_BOTTOM  || this == BACK_BOT_RIGHT;
    }

}