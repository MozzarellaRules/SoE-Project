package entity;

import java.awt.image.BufferedImage;

public class ImageAnimator {
	
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	public ImageAnimator() {
		playedOnce = false;
	}

	/**
	 * SETTER
	 * @param frames set the array that contains the images that must be shown in sequence
	 */
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	/**
	 *
	 * @param i set one of the specific image of an animation
	 */
	public void setFrame(int i) {
		currentFrame = i;
	}

	/**
	 *
	 * @return the current image of the animation
	 */
	public int getFrame() {
		return currentFrame;
	}

	/**
	 *
	 * @return if the animation was played
	 */
	public boolean hasPlayedOnce() {
		return playedOnce;
	}

	/**
	 *
	 * @return the current image
	 */
	public BufferedImage getImage() {
		return frames[currentFrame];
	}

	/**
	 * SETTER
	 * @param d set the time which must pass between an image and an other
	 */
	public void setDelay(long d) {
		delay = d;
	}

	/**
	 * This method updates the field current frame. In this way the getImage() method return the right image.
	 * Also, the field "playedOnce" is setted to true when it arrives at the end of "frames"
	 */
	public void update() {
		if(delay == -1)
			return;
		long elapsed = (System.nanoTime()-startTime)/1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
}
