/*
 * Copyright (c) 2011-2013 Jarek Sacha. All Rights Reserved.
 *
 * Author's e-mail: jpsacha at gmail.com
 */

package opencv2_cookbook.chapter10

import com.googlecode.javacv.cpp.opencv_highgui._


/** The example for section "Tracking feature points in video" in Chapter 10, page 266.
  *
  * Tracking is implemented in class `FeatureTracker`.
  *
  * Unlike in the C++ implementation, we do not need to pass `FeatureTracker` object as a parameter to `VideoProcessor`.
  * We only need to pass to `VideoProcessor` method `process` with its "context".
  * This simplifies implementation of `VideoProcessor`.
  * Scala can treat `process` as a "closure", that is method `process` will have access to all local member variables
  * in class `FeatureTracker`.
  */
object Ex4TrackingFeatures extends App {

    // Open video file
    val capture = cvCreateFileCapture("data/bike.avi")

    // Feature tracker
    val tracker = new FeatureTracker()

    // Create video processor instance
    val processor = new VideoProcessor(capture)
    // Declare a window to display input and output the video
    processor.displayInput = "Input Video"
    processor.displayOutput = "Output Video"
    // Play the video at the original frame rate
    processor.delay = math.round(1000d / processor.frameRate)
    // Set the frame processor callback function (pass FeatureTracker `process` method as a closure)
    processor.frameProcessor = tracker.process

    // Start the process
    processor.run()

    // Close the video file
    cvReleaseCapture(capture)

    println("Done.")
}
