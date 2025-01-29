package org.firstinspires.ftc.teamcode.auto;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class SampleProcessor implements VisionProcessor
{
    Rect largestSample;
    Mat hsvMat;

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        hsvMat = new Mat();
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos)
    {
        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

}
