# Batch Watermarker

A simple shell utility for adding a watermark image to a collection of source images.

## Usage

```
java WaterMarker -watermark logo.png -source My_Pictures/ -padding 38 -position TOP_LEFT
```

Images are output in the directory of the program source files in a folder 'processed_images'.

## Parameters

* `-watermark` The watermark image
* `-source` The source images, can be a single file or directory or a combination of both
* `-position` The location of the watermark, can be TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT or BOTTOM_RIGHT
* `-padding` The space between the edge of the image and the watermark in pixels (default 20)
