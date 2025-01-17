package com.zhuorui.securities.market.stockChart.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.zhuorui.securities.market.stockChart.*;
import com.zhuorui.securities.market.stockChart.data.TimeDataManage;
import com.zhuorui.securities.market.stockChart.enums.ChartType;
import com.zhuorui.securities.market.stockChart.event.BaseEvent;
import com.zhuorui.securities.market.stockChart.model.CirclePositionTime;
import com.zhuorui.securities.market.stockChart.model.TimeDataModel;
import com.zhuorui.securities.market.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.VolFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.CommonUtil;
import com.github.mikephil.charting.utils.NumberUtils;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 当日分时图view
 */
public class OneDayView extends BaseView {

    private Context mContext;
    TimeLineChart lineChart;
    TimeBarChart barChart;
    FrameLayout cirCleView;

    private LineDataSet d1, d2;
    private BarDataSet barDataSet;

    TimeXAxis xAxisLine;
    YAxis axisRightLine;
    YAxis axisLeftLine;

    TimeXAxis xAxisBar;
    YAxis axisLeftBar;
    YAxis axisRightBar;

    private int maxCount = ChartType.HK_ONE_DAY.getPointNum();//最大可见数量，即分时一天最大数据点数
    private SparseArray<String> xLabels = new SparseArray<>();//X轴刻度label
    private TimeDataManage mData;
    private int[] colorArray;

    public OneDayView(Context context) {
        this(context, null);
    }

    public OneDayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_time, this);
        lineChart = (TimeLineChart) findViewById(R.id.line_chart);
        barChart = (TimeBarChart) findViewById(R.id.bar_chart);
        cirCleView = (FrameLayout) findViewById(R.id.circle_frame_time);

        EventBus.getDefault().register(this);

        colorArray = new int[]{ ContextCompat.getColor(mContext, R.color.up_color), ContextCompat.getColor(mContext, R.color.equal_color),ContextCompat.getColor(mContext, R.color.down_color)};

        playHeartbeatAnimation(cirCleView.findViewById(R.id.anim_view));

    }

    /**
     * 初始化图表属性
     */
    public void initChart(boolean landscape) {
        this.landscape = landscape;
        //主图
        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(ContextCompat.getColor(mContext, R.color.border_color));
        lineChart.setBorderWidth(0.7f);
        lineChart.setNoDataText(getResources().getString(R.string.loading));
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);
        lineChart.setDescription(null);
        //副图
        barChart.setScaleEnabled(false);
        barChart.setDrawBorders(true);
        barChart.setBorderColor(ContextCompat.getColor(mContext, R.color.border_color));
        barChart.setBorderWidth(0.7f);
        barChart.setNoDataText(getResources().getString(R.string.loading));
        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setEnabled(false);
        barChart.setDescription(null);

        //主图X轴
        xAxisLine = (TimeXAxis) lineChart.getXAxis();
        xAxisLine.setDrawAxisLine(false);
        xAxisLine.setTextColor(ContextCompat.getColor(mContext, R.color.label_text));
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisLine.setAvoidFirstLastClipping(true);
        xAxisLine.setGridColor(ContextCompat.getColor(mContext, R.color.grid_color));
        xAxisLine.setGridLineWidth(0.7f);

        //主图左Y轴
        axisLeftLine = lineChart.getAxisLeft();
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawGridLines(false);
        axisLeftLine.setValueLineInside(true);
        axisLeftLine.setDrawTopBottomGridLine(false);
        axisLeftLine.setDrawAxisLine(false);
        axisLeftLine.setPosition(landscape ? YAxis.YAxisLabelPosition.OUTSIDE_CHART : YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftLine.setTextColor(ContextCompat.getColor(mContext, R.color.axis_text));
        axisLeftLine.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return NumberUtils.keepPrecisionR(value, precision);
            }
        });

        //主图右Y轴
        axisRightLine = lineChart.getAxisRight();
        axisRightLine.setLabelCount(5, true);
        axisRightLine.setDrawTopBottomGridLine(false);
        axisRightLine.setDrawGridLines(true);
        axisRightLine.setGridLineWidth(0.7f);
        axisRightLine.enableGridDashedLine(CommonUtil.dip2px(mContext, 4), CommonUtil.dip2px(mContext, 3), 0);
        axisRightLine.setDrawAxisLine(false);
        axisRightLine.setValueLineInside(true);
        axisRightLine.setPosition(landscape ? YAxis.YAxisLabelPosition.OUTSIDE_CHART : YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisRightLine.setGridColor(ContextCompat.getColor(mContext, R.color.grid_color));
        axisRightLine.setTextColor(ContextCompat.getColor(mContext, R.color.axis_text));
        axisRightLine.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00%");
                return mFormat.format(value);
            }
        });

        //副图X轴
        xAxisBar = (TimeXAxis) barChart.getXAxis();
        xAxisBar.setDrawLabels(false);
        xAxisBar.setDrawAxisLine(false);
        xAxisBar.setTextColor(ContextCompat.getColor(mContext, R.color.label_text));
        xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBar.setAvoidFirstLastClipping(true);
        xAxisBar.setGridColor(ContextCompat.getColor(mContext, R.color.grid_color));
        xAxisBar.setGridLineWidth(0.7f);

        //副图左Y轴
        axisLeftBar = barChart.getAxisLeft();
        axisLeftBar.setDrawGridLines(false);
        axisLeftBar.setDrawAxisLine(false);
        axisLeftBar.setTextColor(ContextCompat.getColor(mContext, R.color.axis_text));
        axisLeftBar.setPosition(landscape ? YAxis.YAxisLabelPosition.OUTSIDE_CHART : YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftBar.setDrawLabels(true);
        axisLeftBar.setLabelCount(2, true);
        axisLeftBar.setAxisMinimum(0);
        axisLeftBar.setSpaceTop(5);
        axisLeftBar.setValueLineInside(true);

        //副图右Y轴
        axisRightBar = barChart.getAxisRight();
        axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(true);
        axisRightBar.setDrawAxisLine(false);
        axisRightBar.setLabelCount(3, true);
        axisRightBar.setDrawTopBottomGridLine(false);
        axisRightBar.setGridColor(ContextCompat.getColor(mContext, R.color.grid_color));
        axisRightBar.setGridLineWidth(0.7f);
        axisRightBar.enableGridDashedLine(CommonUtil.dip2px(mContext, 4), CommonUtil.dip2px(mContext, 3), 0);

        //手势联动监听
        gestureListenerLine = new CoupleChartGestureListener(lineChart, new Chart[]{barChart});
        gestureListenerBar = new CoupleChartGestureListener(barChart, new Chart[]{lineChart});
        lineChart.setOnChartGestureListener(gestureListenerLine);
        barChart.setOnChartGestureListener(gestureListenerBar);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                lineChart.highlightValue(h);
                barChart.highlightValue(new Highlight(h.getX(), h.getDataSetIndex(), -1));
                if (mHighlightValueSelectedListener != null) {
                    mHighlightValueSelectedListener.onDayHighlightValueListener(mData, e.getXIndex(), true);
                }
            }

            @Override
            public void onNothingSelected() {
                barChart.highlightValues(null);
                if (mHighlightValueSelectedListener != null) {
                    mHighlightValueSelectedListener.onDayHighlightValueListener(mData, 0, false);
                }
            }
        });
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                barChart.highlightValue(h);
                lineChart.highlightValue(new Highlight(h.getX(), h.getDataSetIndex(), -1));
                if (mHighlightValueSelectedListener != null) {
                    mHighlightValueSelectedListener.onDayHighlightValueListener(mData, e.getXIndex(), true);
                }
            }

            @Override
            public void onNothingSelected() {
                lineChart.highlightValues(null);
                if (mHighlightValueSelectedListener != null) {
                    mHighlightValueSelectedListener.onDayHighlightValueListener(mData, 0, false);
                }
            }
        });

    }

    /**
     * 是否显示坐标轴label
     *
     * @param isShow
     */
    private void setShowLabels(boolean isShow) {
        lineChart.getAxisLeft().setDrawLabels(isShow);
        lineChart.getAxisRight().setDrawLabels(isShow);
        lineChart.getXAxis().setDrawLabels(isShow);
        barChart.getAxisLeft().setDrawLabels(isShow);
    }

    /**
     * 设置分时数据
     *
     * @param mData
     */
    public void setDataToChart(TimeDataManage mData) {
        if (mData == null) return;
        this.mData = mData;
        if (mData.getDatas().size() == 0) {
            cirCleView.setVisibility(View.GONE);
            lineChart.setNoDataText(getResources().getString(R.string.no_data));
            barChart.setNoDataText(getResources().getString(R.string.no_data));
        } else {
            cirCleView.setVisibility(landscape ? View.VISIBLE : View.GONE);
        }

        if (mData.getAssetId().endsWith(".HK")) {
            setPrecision(mData.getAssetId().contains("IDX") ? 2 : 3);
            setMaxCount(ChartType.HK_ONE_DAY.getPointNum());
        } else if (mData.getAssetId().endsWith(".US")) {
            if (Math.abs(mData.getMax()) < 1) {
                setPrecision(4);
            } else {
                setPrecision(2);
            }
            setMaxCount(ChartType.US_ONE_DAY.getPointNum());
        } else {
            setPrecision(2);
            setMaxCount(ChartType.ONE_DAY.getPointNum());
        }
        setXLabels(mData.getOneDayXLabels(landscape));
        setShowLabels(true);
        setMarkerView(mData);
        setBottomMarkerView(mData);

        axisLeftLine.setAxisMinimum(mData.getMin());
        axisLeftLine.setAxisMaximum(mData.getMax());

        //Y轴label渲染颜色
        Transformer leftYTransformer = lineChart.getRendererLeftYAxis().getTransformer();
        ColorContentYAxisRenderer leftColorContentYAxisRenderer = new ColorContentYAxisRenderer(lineChart.getViewPortHandler(), axisLeftLine, leftYTransformer);
        leftColorContentYAxisRenderer.setLabelColor(colorArray);
        leftColorContentYAxisRenderer.setClosePrice(mData.getPreClose());
        leftColorContentYAxisRenderer.setLandscape(landscape);
        lineChart.setRendererLeftYAxis(leftColorContentYAxisRenderer);

        //Y轴label渲染颜色
        Transformer rightYTransformer = lineChart.getRendererRightYAxis().getTransformer();
        ColorContentYAxisRenderer rightColorContentYAxisRenderer = new ColorContentYAxisRenderer(lineChart.getViewPortHandler(), axisRightLine, rightYTransformer);
        rightColorContentYAxisRenderer.setLabelColor(colorArray);
        rightColorContentYAxisRenderer.setClosePrice(mData.getPreClose());
        rightColorContentYAxisRenderer.setLandscape(landscape);
        lineChart.setRendererRightYAxis(rightColorContentYAxisRenderer);

        if (Float.isNaN(mData.getPercentMax()) || Float.isNaN(mData.getPercentMin())||Float.isNaN(mData.getVolMaxTime())) {
            axisLeftBar.setAxisMaximum(0);
            axisRightLine.setAxisMinimum(-0.01f);
            axisRightLine.setAxisMaximum(0.01f);
        } else {
            axisLeftBar.setAxisMaximum(mData.getVolMaxTime());
            axisRightLine.setAxisMinimum(mData.getPercentMin());
            axisRightLine.setAxisMaximum(mData.getPercentMax());
        }

        axisLeftBar.setValueFormatter(new VolFormatter(mContext, mData.getAssetId()));

        ArrayList<Entry> lineCJEntries = new ArrayList<>();
        ArrayList<Entry> lineJJEntries = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
            TimeDataModel t = mData.getDatas().get(j);
            if (t == null) {
                lineCJEntries.add(new Entry(i, i, Float.NaN));
                lineJJEntries.add(new Entry(i, i, Float.NaN));
                barEntries.add(new BarEntry(i, i, Float.NaN));
                continue;
            }
            lineCJEntries.add(new Entry(i, i, (float) mData.getDatas().get(i).getNowPrice()));
            lineJJEntries.add(new Entry(i, i, (float) mData.getDatas().get(i).getAveragePrice()));
            barEntries.add(new BarEntry(i, i, mData.getDatas().get(i).getVolume()));
        }
        d1 = new LineDataSet(lineCJEntries, "分时线");
        d2 = new LineDataSet(lineJJEntries, "均价");
        d1.setDrawCircleDashMarker(landscape);
        d2.setDrawCircleDashMarker(false);
        d1.setDrawValues(false);
        d2.setDrawValues(false);
        d1.setLineWidth(0.7f);
        d2.setLineWidth(0.7f);
        d1.setColor(ContextCompat.getColor(mContext, R.color.minute_blue));
        d2.setColor(ContextCompat.getColor(mContext, R.color.minute_yellow));
        d1.setDrawFilled(true);
        d1.setFillColor(ContextCompat.getColor(mContext, R.color.fill_Color));
        d1.setHighLightColor(ContextCompat.getColor(mContext, R.color.highLight_Color));
        d1.setHighlightEnabled(landscape);
        d2.setHighlightEnabled(false);
        d1.setDrawCircles(false);
        d2.setDrawCircles(false);
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
        d1.setPrecision(precision);
        d1.setTimeDayType(1);//设置分时图类型
        d2.setTimeDayType(1);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);
        LineData cd = new LineData(sets);
        lineChart.setData(cd);

        barDataSet = new BarDataSet(barEntries, "成交量");
        barDataSet.setHighLightColor(ContextCompat.getColor(mContext, R.color.highLight_Color));
        barDataSet.setHighlightEnabled(landscape);
        barDataSet.setDrawValues(false);
        barDataSet.setNeutralColor(ContextCompat.getColor(mContext, R.color.equal_color));
        barDataSet.setIncreasingColor(ContextCompat.getColor(mContext, R.color.up_color));
        barDataSet.setDecreasingColor(ContextCompat.getColor(mContext, R.color.down_color));
        barDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        barDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        //请注意，修改视口的所有方法需要在为Chart设置数据之后调用。
        //设置当前视图四周的偏移量。 设置这个，将阻止图表自动计算它的偏移量。使用 resetViewPortOffsets()撤消此设置。
        if (landscape) {
            float volwidth = Utils.calcTextWidthForVol(mPaint, mData.getVolMaxTime());
            float pricewidth = Utils.calcTextWidth(mPaint, NumberUtils.keepPrecision(Float.isNaN(mData.getMax())?"0":mData.getMax() + "", precision) + "#");
            float left = CommonUtil.dip2px(mContext, pricewidth > volwidth ? pricewidth : volwidth);
            float right = CommonUtil.dip2px(mContext, Utils.calcTextWidth(mPaint, "-10.00%"));
            lineChart.setViewPortOffsets(left, CommonUtil.dip2px(mContext, 5), right, CommonUtil.dip2px(mContext, 15));
            barChart.setViewPortOffsets(left, 0, right, CommonUtil.dip2px(mContext, 15));
        } else {
            lineChart.setViewPortOffsets(CommonUtil.dip2px(mContext, 5), CommonUtil.dip2px(mContext, 5), CommonUtil.dip2px(mContext, 5), CommonUtil.dip2px(mContext, 15));
            barChart.setViewPortOffsets(CommonUtil.dip2px(mContext, 5), 0, CommonUtil.dip2px(mContext, 5), CommonUtil.dip2px(mContext, 5));
        }

        //下面方法需在填充数据后调用
        xAxisLine.setXLabels(getXLabels());
        xAxisLine.setLabelCount(getXLabels().size(), false);
        xAxisBar.setXLabels(getXLabels());
        xAxisBar.setLabelCount(getXLabels().size(), false);
        lineChart.setVisibleXRange(maxCount, maxCount);
        barChart.setVisibleXRange(maxCount, maxCount);
        //moveViewTo(...) 方法会自动调用 invalidate()
        lineChart.moveViewToX(mData.getDatas().size() - 1);
        barChart.moveViewToX(mData.getDatas().size() - 1);

    }

    /**
     * 动态增加一个点数据
     * @param timeDatamodel
     * @param length
     */
    public void dynamicsAddOne(TimeDataModel timeDatamodel, int length) {
        int index = length - 1;
        LineData lineData = lineChart.getData();
        ILineDataSet d1 = lineData.getDataSetByIndex(0);
        d1.addEntry(new Entry(index, index, (float) timeDatamodel.getNowPrice()));
        ILineDataSet d2 = lineData.getDataSetByIndex(1);
        d2.addEntry(new Entry(index, index, (float) timeDatamodel.getAveragePrice()));

        BarData barData = barChart.getData();
        IBarDataSet barDataSet = barData.getDataSetByIndex(0);
        barDataSet.addEntry(new BarEntry(index, index, timeDatamodel.getVolume()));
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        barData.notifyDataChanged();
        barChart.notifyDataSetChanged();
        lineChart.setVisibleXRange(maxCount, maxCount);
        barChart.setVisibleXRange(maxCount, maxCount);
        //动态添加或移除数据后， 调用invalidate()刷新图表之前 必须调用 notifyDataSetChanged() .
        lineChart.moveViewToX(index);
        barChart.moveViewToX(index);
    }

    /**
     * 动态更新最后一点数据
     * @param timeDatamodel
     * @param length
     */
    public void dynamicsUpdateOne(TimeDataModel timeDatamodel, int length) {
        int index = length - 1;
        LineData lineData = lineChart.getData();
        ILineDataSet d1 = lineData.getDataSetByIndex(0);
        Entry e = d1.getEntryForIndex(index);
        d1.removeEntry(e);
        d1.addEntry(new Entry(index, index, (float) timeDatamodel.getNowPrice()));

        ILineDataSet d2 = lineData.getDataSetByIndex(1);
        Entry e2 = d2.getEntryForIndex(index);
        d2.removeEntry(e2);
        d2.addEntry(new Entry(index, index, (float) timeDatamodel.getAveragePrice()));

        BarData barData = barChart.getData();
        IBarDataSet barDataSet = barData.getDataSetByIndex(0);
        barDataSet.removeEntry(index);
        barDataSet.addEntry(new BarEntry(index, index, timeDatamodel.getVolume()));

        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.moveViewToX(index);

        barData.notifyDataChanged();
        barChart.notifyDataSetChanged();
        barChart.moveViewToX(index);
    }

    public void cleanData() {
        if (lineChart != null && lineChart.getLineData() != null) {
            setShowLabels(false);
            lineChart.clearValues();
            barChart.clearValues();
        }
        if (cirCleView != null) {
            cirCleView.setVisibility(View.GONE);
        }
    }

    private void setMarkerView(TimeDataManage mData) {
        LeftMarkerView leftMarkerView = new LeftMarkerView(mContext, R.layout.my_markerview, precision);
        TimeRightMarkerView rightMarkerView = new TimeRightMarkerView(mContext, R.layout.my_markerview);
        lineChart.setMarker(leftMarkerView, rightMarkerView, mData);
    }

    private void setBottomMarkerView(TimeDataManage kDatas) {
        BarBottomMarkerView bottomMarkerView = new BarBottomMarkerView(mContext, R.layout.my_markerview);
        barChart.setMarker(bottomMarkerView, kDatas);
    }


    @Override
    public void onEventMainThread(BaseEvent event) {
        if (event.method == 1) {
            CirclePositionTime position = (CirclePositionTime) event.obj;
            cirCleView.setX(position.cx - cirCleView.getWidth() / 2);
            cirCleView.setY(position.cy - cirCleView.getHeight() / 2);
        }
    }

    public void setXLabels(SparseArray<String> xLabels) {
        this.xLabels = xLabels;
    }

    public SparseArray<String> getXLabels() {
        if (xLabels.size() == 0) {
            setMaxCount(ChartType.HK_ONE_DAY.getPointNum());
            xLabels.put(0, "09:30");
            xLabels.put(60, "10:30");
            xLabels.put(120, "11:30");
            xLabels.put(180, "13:30");
            xLabels.put(240, "14:30");
            xLabels.put(300, "15:30");
            xLabels.put(330, "16:00");
        }
        return xLabels;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void eventBusUnregister() {
        EventBus.getDefault().unregister(this);
    }

    public TimeDataManage getData() {
        return mData;
    }
}
