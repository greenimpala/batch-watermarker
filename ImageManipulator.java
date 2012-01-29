package src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;

public class ImageManipulator {
	
	public BufferedImage loadImage(File file) throws IOException{
		BufferedImage image = ImageIO.read(file);
		return image;
	}
	
	public File[] buildImagePaths(String[] fileNames) throws IOException{

		Vector<File> targetImagePaths = new Vector<File>();
		
		for (int i=0; i<fileNames.length; i++){
			File filePath = new File(fileNames[i]);
			
			if (filePath.isFile() && fileIsImage(filePath)) {
				targetImagePaths.add(filePath);
			} else if (filePath.isDirectory()){ // Directory
				targetImagePaths.addAll(getImagePathsInsideDirectory(filePath));
			}
		}
		
		if (targetImagePaths.size() > 0) {
			return targetImagePaths.toArray(new File[0]);
		} else {
			throw new IOException("Could not find any valid source files.");
		}
	}
	
	public Vector<File> getImagePathsInsideDirectory(File directory) {
		Vector<File> targetImagePaths = new Vector<File>();
		String[] directoryFilePaths = directory.list();
		
		for (int i=0; i<directoryFilePaths.length; i++){
			String fullPath = directory.getPath() + "/" + directoryFilePaths[i];
			File filePath = new File(fullPath);
			if(filePath.isFile() && fileIsImage(filePath)){
				targetImagePaths.add(filePath);
			}
		}
		return targetImagePaths;
	}
	
	public boolean fileIsImage(File file) {
		if(file.getName().matches("(?i).*(.jpg|.jpeg|.png)")){
			return true;
		}
		return false;
	}
	
	public BufferedImage addWatermark(BufferedImage watermark, BufferedImage nonWatermarkedImage, String position, Coordinates padding) {
		int xStartPixel = position.matches("TOP_LEFT|BOTTOM_LEFT") ? padding.getXCoord() : nonWatermarkedImage.getWidth() - (watermark.getWidth() + padding.getXCoord()) ;
		int yStartPixel = position.matches("TOP_LEFT|TOP_RIGHT") ? padding.getYCoord() : nonWatermarkedImage.getHeight() - (watermark.getHeight() + padding.getYCoord()) ;
		
		for(int i = xStartPixel; i < watermark.getWidth() + xStartPixel; i++){
			for(int j = yStartPixel; j < watermark.getHeight() + yStartPixel; j++){
				int watermarkRGB = watermark.getRGB(i-xStartPixel,j-yStartPixel);
				if (((watermarkRGB >>24) & 0xff) > 0) { // Not a transparent pixel
					nonWatermarkedImage.setRGB(i, j, watermarkRGB);
				}
			}
		}
		return nonWatermarkedImage;
	}
	
}
