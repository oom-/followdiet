package oom.followdiet.Classes;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ProgressBarAnimation extends Animation {
    private ProgressBar mProgressBar;
    private int mTo;
    private int mFrom;
    private long mStepDuration;
    private long fullDuration;


    /**
     * @param fullDuration - time required to fill progress from 0% to 100%
     */
    public ProgressBarAnimation(ProgressBar progressBar, long fullDuration) {
        super();
        mProgressBar = progressBar;
        this.fullDuration = fullDuration;
    }

    public void setMax(int max)
    {
        mProgressBar.setMax(max);
        if (mProgressBar.getMax() > 0)
            mStepDuration = (fullDuration / mProgressBar.getMax());
    }

    public void refresh()
    {
           mProgressBar.setProgress(0);
           mFrom = 0;
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }

        if (progress > mProgressBar.getMax()) {
            progress = mProgressBar.getMax();
        }

        mTo = progress;

        mFrom = mProgressBar.getProgress();
        setDuration(Math.abs(mTo - mFrom) * mStepDuration);
        mProgressBar.startAnimation(this);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float value = mFrom + (mTo - mFrom) * interpolatedTime;
        mProgressBar.setProgress((int) value);
    }
}