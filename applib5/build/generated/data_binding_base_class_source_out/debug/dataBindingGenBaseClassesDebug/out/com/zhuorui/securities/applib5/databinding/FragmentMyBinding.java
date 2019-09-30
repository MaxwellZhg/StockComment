package com.zhuorui.securities.applib5.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.zhuorui.securities.applib5.ui.viewmodel.MyTabViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentMyBinding extends ViewDataBinding {
  @Bindable
  protected MyTabViewModel mViewmodel;

  protected FragmentMyBinding(Object _bindingComponent, View _root, int _localFieldCount) {
    super(_bindingComponent, _root, _localFieldCount);
  }

  public abstract void setViewmodel(@Nullable MyTabViewModel viewmodel);

  @Nullable
  public MyTabViewModel getViewmodel() {
    return mViewmodel;
  }

  @NonNull
  public static FragmentMyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_my, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentMyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentMyBinding>inflateInternal(inflater, com.zhuorui.securities.applib5.R.layout.fragment_my, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentMyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_my, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentMyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentMyBinding>inflateInternal(inflater, com.zhuorui.securities.applib5.R.layout.fragment_my, null, false, component);
  }

  public static FragmentMyBinding bind(@NonNull View view) {
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
  public static FragmentMyBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentMyBinding)bind(component, view, com.zhuorui.securities.applib5.R.layout.fragment_my);
  }
}
