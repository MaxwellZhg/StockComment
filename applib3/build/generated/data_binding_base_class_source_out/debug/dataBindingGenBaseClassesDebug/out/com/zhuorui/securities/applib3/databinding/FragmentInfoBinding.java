package com.zhuorui.securities.applib3.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.zhuorui.securities.applib3.ui.viewmodel.InfomationViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentInfoBinding extends ViewDataBinding {
  @Bindable
  protected InfomationViewModel mViewmodel;

  protected FragmentInfoBinding(Object _bindingComponent, View _root, int _localFieldCount) {
    super(_bindingComponent, _root, _localFieldCount);
  }

  public abstract void setViewmodel(@Nullable InfomationViewModel viewmodel);

  @Nullable
  public InfomationViewModel getViewmodel() {
    return mViewmodel;
  }

  @NonNull
  public static FragmentInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_info, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentInfoBinding>inflateInternal(inflater, com.zhuorui.securities.applib3.R.layout.fragment_info, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_info, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentInfoBinding>inflateInternal(inflater, com.zhuorui.securities.applib3.R.layout.fragment_info, null, false, component);
  }

  public static FragmentInfoBinding bind(@NonNull View view) {
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
  public static FragmentInfoBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentInfoBinding)bind(component, view, com.zhuorui.securities.applib3.R.layout.fragment_info);
  }
}
