package com.zhuorui.securities.base2app.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

/**
 * Created by bobi on 2017/1/16.
 */

public class AnimationBuild {
    //Property Animation属性动画
    public static final String ALPHA="alpha";//透明
    public static final String TRANSLATION_Y="translationY";//位移Y轴
    public static final String TRANSLATION_X="translationX";//位移X轴
    public static final String ROTATION_X="rotationX";//X轴旋转
    public static final String ROTATION_Y="rotationY";//Y轴旋转
    public static final String ROTATION="rotation";//普通旋转
    public static final String SCALE_X="scaleX";//缩放X轴
    public static final String SCALE_Y="scaleY";//缩放Y轴

    /**
     * 动画集合的builder
     */
    public static final class AnimatorSetBuilder{
        private AnimatorSet animatorSet;

        public AnimatorSetBuilder(){
            animatorSet=new AnimatorSet();
        }

        /**
         * 设置动画集合的Together运行方式
         * @param animators
         * @return
         */
        public AnimatorSetBuilder animatorSetTogether(ObjectAnimator... animators){
            animatorSet.playTogether(animators);
            return this;
        }

        public AnimatorSetBuilder animatorSetBefore(ObjectAnimator animator1,ObjectAnimator animator2){
            animatorSet.play(animator1).before(animator2);
            return this;
        }

        /**
         * 设置动画集合的运行时间
         * @param duration
         * @return
         */
        public AnimatorSetBuilder duration(long duration){
            animatorSet.setDuration(duration);
            return this;
        }
        /**
         * 设置动画集合的延迟时间
         * @param delay
         * @return
         */
        public AnimatorSetBuilder delay(long delay){
            animatorSet.setStartDelay(delay);
            return this;
        }

        /**
         * 设置监听
         * @param listener
         * @return
         */
        public AnimatorSetBuilder addListener(Animator.AnimatorListener listener){
            animatorSet.addListener(listener);
            return this;
        }

        /**
         * 设置插值器
         * @param interpolator
         * @return
         */
        public AnimatorSetBuilder interpolator(Interpolator interpolator){
            animatorSet.setInterpolator(interpolator);
            return this;
        }

        /**
         * 返回动画集合
         * @return
         */
        public AnimatorSet build(){
            return animatorSet;
        }
    }

    /**
     * 属性动画的builder
     */
    public static final class PropertyAnimationBuilder {
        private ObjectAnimator animator;

        /**
         * 创建动画
         * @param object
         * @param animation
         * @return
         */
        public PropertyAnimationBuilder animation(Object object, String animation, float... values){
            if (null==object||null==animation||animation.isEmpty()){
                return null;
            }

            animator = new ObjectAnimator().ofFloat(object,animation,values);
            return this;
        }

        /**
         * 设置动画运行时间
         * @param duration
         * @return
         */
        public PropertyAnimationBuilder duration(long duration){
            animator.setDuration(duration);
            return this;
        }

        /**
         * 设置动画延迟时间
         * @param delay
         * @return
         */
        public PropertyAnimationBuilder delay(long delay){
            animator.setStartDelay(delay);
            return this;
        }

        /**
         * 设置监听
         * @param listener
         * @return
         */
        public PropertyAnimationBuilder addListener(Animator.AnimatorListener listener){
            animator.addListener(listener);
            return this;
        }

        /**
         * 设置插值器
         * @param interpolator
         * @return
         */
        public PropertyAnimationBuilder interpolator(Interpolator interpolator){
            animator.setInterpolator(interpolator);
            return this;
        }

        /**
         * 返回动画
         * @return
         */
        public ObjectAnimator build(){
            return animator;
        }

    }

    /**
     * 创建布局动画
     * @param viewGroup
     * @param animation
     * @param delay
     */
    public static void createLayoutAnimation(ViewGroup viewGroup,Animation animation, float delay){
        LayoutAnimationController layoutAnimationController=new LayoutAnimationController(animation,delay);
        viewGroup.setLayoutAnimation(layoutAnimationController);
    }
}
