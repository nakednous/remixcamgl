package remixlab.remixcamgl.gl2;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import remixlab.remixcam.core.Constants;
import remixlab.remixcam.core.Renderable;
import remixlab.remixcam.geom.*;

public class GL2Renderer implements Renderable, Constants {
	public GLAutoDrawable drawable;

	public GL2Renderer(GLAutoDrawable d) {
		drawable = d;
	}
	
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
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glPushMatrix();
	}

	/**
	 * Replace the current transformation matrix with the top of the stack.
	 */
	@Override
	public void popMatrix() {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glPopMatrix();
	}

	/**
	 * Translate in X and Y.
	 */
	@Override
	public void translate(float tx, float ty) {
		translate(tx, ty, 0);
	}

	/**
	 * Translate in X, Y, and Z.
	 */
	@Override
	public void translate(float tx, float ty, float tz) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glTranslatef(tx, ty, tz);
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
	}

	/**
	 * Rotate around the X axis.
	 */
	@Override
	public void rotateX(float angle) {
	}

	/**
	 * Rotate around the Y axis.
	 */
	@Override
	public void rotateY(float angle) {
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
	}

	/**
	 * Rotate about a vector in space. Same as the glRotatef() function, but angle is expressed
	 * in radians.
	 */
	@Override
	public void rotate(float angle, float vx, float vy, float vz) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glRotatef(angle*180/PI, vx, vy, vz);
	}

	/**
	 * Scale in all dimensions.
	 */
	@Override
	public void scale(float s) {
		scale(s, s, s);
	}

	/**
	 * Scale in X and Y. Equivalent to scale(sx, sy, 1).
	 * 
	 * Not recommended for use in 3D, because the z-dimension is just scaled by
	 * 1, since there's no way to know what else to scale it by.
	 */
	@Override
	public void scale(float sx, float sy) {
		scale(sx, sy, 1);
	}

	/**
	 * Scale in X, Y, and Z.
	 */
	@Override
	public void scale(float x, float y, float z) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glScalef(x, y, z);
	}	

	@Override
	public void loadIdentity() {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glLoadIdentity();
	}

	/**
	 * Set the current transformation matrix to identity.
	 */
	@Override
	public void resetMatrix() {
		loadIdentity();
	}

	@Override
	public void loadMatrix(Matrix3D source) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glLoadMatrixf(source.mat, 0);
	}

	@Override
	public void multiplyMatrix(Matrix3D source) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glMultMatrixf(source.mat, 0);
	}

	@Override
	public void applyMatrix(Matrix3D source) {
		multiplyMatrix(source);
	}	

	/**
	 * Apply a 4x4 transformation matrix.
	 */
	@Override
	public void applyMatrixRowMajorOrder(float n00, float n01, float n02, float n03,
							             float n10, float n11, float n12, float n13,
							             float n20, float n21, float n22, float n23,
							             float n30, float n31, float n32, float n33) {
		float [] m = new float [] {n00, n01, n02, n03,
				                   n10, n11, n12, n13,
				                   n20, n21, n22, n23,
				                   n30, n31, n32, n33};
		
		Matrix3D mat = new Matrix3D(m, true);
		applyMatrix(mat);
	}

	@Override
	public void frustum(float left, float right, float bottom, float top, float znear, float zfar) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glFrustumf(left, right, bottom, top, znear, zfar);
	}

	@Override
	public Matrix3D getMatrix() {
		//TODO implement
		return null;
	}

	/**
	 * Copy the current transformation matrix into the specified target. Pass in
	 * null to create a new matrix.
	 */
	@Override
	public Matrix3D getMatrix(Matrix3D target) {
		//TODO implement
		return null;
	}

	/**
	 * Set the current transformation matrix to the contents of another.
	 */
	@Override
	public void setMatrix(Matrix3D source) {
		loadMatrix(source);
	}

	/**
	 * Print the current model (or "transformation") matrix.
	 */
	@Override
	public void printMatrix() {		
	}

	@Override
	public void matrixMode(int mode) {
		GL2 gl2 = drawable.getGL().getGL2();
		if( mode == MODELVIEW )
			gl2.glMatrixMode(GL2.GL_MODELVIEW);
		else
			gl2.glMatrixMode(GL2.GL_PROJECTION);
	}
	
	@Override
	public int getMatrixMode() {
		// TODO implement me!
		return 0;
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
