package util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ThumbGenerator {

	public static boolean generate(File source, File dest, int height, int width)
			throws IOException {
		if (source != null && dest != null) {
			FileInputStream input = new FileInputStream(source);
			try {
				BufferedImage bufImage = ImageIO.read(input);
				if (bufImage != null) {
					width = width > bufImage.getWidth() ? bufImage.getWidth() : width;
					height =  bufImage.getHeight();
					double scale = calcScale(bufImage, width, height);
					BufferedImage imageScaled = scale(bufImage, scale);
					FileOutputStream fos = new FileOutputStream(dest);
					ImageIO.write(imageScaled, "JPG", fos);
					fos.close();
					input.close();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private static double calcScale(BufferedImage img, int width, int height) {
		double scale1 = (double) width / (double) img.getWidth();
		double scale2 = (double) height / (double) img.getHeight();
		double scale = scale1 > scale2 ? scale2 : scale1;
		return scale;
	}

	private static BufferedImage scale(BufferedImage img, double scale) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage scaled = new BufferedImage((int) (w * scale),
				(int) (h * scale), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = scaled.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.drawImage(img.getScaledInstance((int) (w * scale),
				(int) (h * scale), Image.SCALE_SMOOTH), 0, 0,
				(int) (w * scale), (int) (h * scale), null);
		g2.dispose();
		return scaled;
	}
}