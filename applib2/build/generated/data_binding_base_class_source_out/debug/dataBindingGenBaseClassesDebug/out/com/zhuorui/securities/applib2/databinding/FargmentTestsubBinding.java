package com.zhuorui.securities.applib2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.zhuorui.securities.applib2.viewmodel.MarketTabViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FargmentTestsubBinding extends ViewDataBinding {
  @NonNull
  public final TextView textviwe;

  @Bindable
  protected MarketTabViewModel mViewmodel;

  protected FargmentTestsubBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView textviwe) {
    super(_bindingComponent, _root, _localFieldCount);
    this.textviwe = textviwe;
  }

  public abstract void setViewmodel(@Nullable MarketTabViewModel viewmodel);

  @Nullable
  public MarketTabViewModel getViewmodel() {
    return mViewmodel;
  }

  @NonNull
  public static FargmentTestsubBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fargment_testsub, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FargmentTestsubBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FargmentTestsubBinding>inflateInternal(inflater, com.zhuorui.securities.applib2.R.layout.fargment_testsub, root, attachToRoot, component);
  }

  @NonNull
  public static FargmentTestsubBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fargment_testsub, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FargmentTestsubBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FargmentTestsubBinding>inflateInternal(inflater, com.zhuorui.securities.applib2.R.layout.fargment_testsub, null, false, component);
  }

  public static FargmentTestsubBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static FargmentTestsubBinding bind(@NonNull View view, @Nullable Object component) {
    return (FargmentTestsubBinding)bind(component, view, com.zhuorui.securities.applib2.R.layout.fargment_testsub);
  }
}
