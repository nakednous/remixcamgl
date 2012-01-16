package remixlab.remixcamgl.gl4;

import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;

import remixlab.remixcam.core.Renderable;
import remixlab.remixcam.geom.*;

public class GL4Renderer implements Renderable {
	public GLAutoDrawable drawable;
	//public GL4 gl4;
	protected MatrixStack mStack;

	public GL4Renderer(GLAutoDrawable d) {
		drawable = d;		
		mStack = new MatrixStack();
	}
	
	/**
	public GL4Renderer(GL4 gl) {
		gl4 = gl;
		mStack = new MatrixStack();
	}
	*/
	
	@Override
	public int getWidth() {
		return drawable.getWidth();
	}
	
	@Override
	public int getHeight() {
		return drawable.getHeight();
	}	

	/**
	 * Push a copy of the current transformation matrix onto the stack.
	 */
	@Override
	public void pushMatrix() {
		mStack.pushMatrix();
	}

	/**
	 * Replace the current transformation matrix with the top of the stack.
	 */
	@Override
	public void popMatrix() {
		mStack.popMatrix();
	}

	/**
	 * Translate in X and Y.
	 */
	@Override
	public void translate(float tx, float ty) {
		mStack.translate(tx, ty);
	}

	/**
	 * Translate in X, Y, and Z.
	 */
	@Override
	public void translate(float tx, float ty, float tz) {
		mStack.translate(tx, ty, tz);
	}

	/**
	 * Two dimensional rotation.
	 * 
	 * Same as rotateZ (this is identical to a 3D rotation along the z-axis) but
	 * included for clarity. It'd be weird for people drawing 2D graphics to be
	 * using rotateZ. And they might kick our a-- for the confusion.
	 * 
	 * <A HREF="http://www.xkcd.com/c184.html">Additional background</A>.
	 */
	@Override
	public void rotate(float angle) {
		mStack.rotate(angle);
	}

	/**
	 * Rotate around the X axis.
	 */
	@Override
	public void rotateX(float angle) {
		mStack.rotateX(angle);
	}

	/**
	 * Rotate around the Y axis.
	 */
	@Override
	public void rotateY(float angle) {
		mStack.rotateY(angle);
	}

	/**
	 * Rotate around the Z axis.
	 * 
	 * The functions rotate() and rotateZ() are identical, it's just that it
	 * make sense to have rotate() and then rotateX() and rotateY() when using
	 * 3D; nor does it make sense to use a function called rotateZ() if you're
	 * only doing things in 2D. so we just decided to have them both be the
	 * same.
	 */
	@Override
	public void rotateZ(float angle) {
		mStack.rotateZ(angle);
	}

	/**
	 * Rotate about a vector in space. Same as the glRotatef() function.
	 */
	@Override
	public void rotate(float angle, float vx, float vy, float vz) {
		mStack.rotate(angle, vx, vy, vz);
	}

	/**
	 * Scale in all dimensions.
	 */
	@Override
	public void scale(float s) {
		mStack.scale(s);
	}

	/**
	 * Scale in X and Y. Equivalent to scale(sx, sy, 1).
	 * 
	 * Not recommended for use in 3D, because the z-dimension is just scaled by
	 * 1, since there's no way to know what else to scale it by.
	 */
	@Override
	public void scale(float sx, float sy) {
		mStack.scale(sx, sy);
	}

	/**
	 * Scale in X, Y, and Z.
	 */
	@Override
	public void scale(float x, float y, float z) {
		mStack.scale(x, y, z);
	}	

	@Override
	public void loadIdentity() {
		mStack.loadIdentity();
	}

	/**
	 * Set the current transformation matrix to identity.
	 */
	@Override
	public void resetMatrix() {
		mStack.resetMatrix();
	}

	@Override
	public void loadMatrix(Matrix3D source) {
		mStack.loadMatrix(source);
	}

	@Override
	public void multiplyMatrix(Matrix3D source) {
		mStack.multiplyMatrix(source);
	}

	@Override
	public void applyMatrix(Matrix3D source) {
		mStack.applyMatrix(source);
	}
	
	/**
	 * Apply a 4x4 transformation matrix.
	 */
	@Override
	public void applyMatrixRowMajorOrder(float n00, float n01, float n02, float n03,
			                             float n10, float n11, float n12, float n13,
			                             float n20, float n21, float n22, float n23,
			                             float n30, float n31, float n32, float n33) {		
		mStack.applyMatrixTransposed(n00, n01, n02, n03,
				                     n10, n11, n12, n13,
				                     n20, n21, n22, n23,
				                     n30, n31, n32, n33);
	}

	@Override
	public void frustum(float left, float right, float bottom, float top,
			float znear, float zfar) {
		mStack.frustum(left, right, bottom, top, znear, zfar);
	}

	@Override
	public Matrix3D getMatrix() {
		return mStack.getMatrix();
	}

	/**
	 * Copy the current transformation matrix into the specified target. Pass in
	 * null to create a new matrix.
	 */
	@Override
	public Matrix3D getMatrix(Matrix3D target) {
		return mStack.getMatrix(target);
	}

	/**
	 * Set the current transformation matrix to the contents of another.
	 */
	@Override
	public void setMatrix(Matrix3D source) {
		mStack.setMatrix(source);
	}

	/**
	 * Print the current model (or "transformation") matrix.
	 */
	@Override
	public void printMatrix() {
		mStack.printMatrix();
	}

	@Override
	public void matrixMode(int mode) {
		mStack.matrixMode(mode);
	}
	
	@Override
	public int getMatrixMode() {
		return mStack.getMatrixMode();
	}

	@Override
	public void beginShape(int kind) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endShape() {
		// TODO Auto-generated method stub

	}

	@Override
	public void vertex(Vector3D v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vertex(float x, float y, float z) {
		// get gl4 and do something with it GL4 gl4 = drawable.getGL().getGL4();
		// TODO Auto-generated method stub
	}

	@Override
	public void stroke(int color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void color(int r, int g, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void strokeWeight(int weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pushStyle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void popStyle() {
		// TODO Auto-generated method stub

	}
}
