package remixlab.remixcamgl.gl2;

import javax.media.opengl.GL2;

import remixlab.remixcam.geom.*;
import remixlab.remixcamgl.Scene;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.*;

/**
 * This class encapsulates a glsl shader. Based in the code by the work of Andres Colubri
 * in Processing-2 which in turn is based on JohnG's (http://www.hardcorepawn.com/).
 */
public class ShaderGL2 {
	Scene scene;
	protected GL2 gl;

	protected int programObject;
	protected int vertexShader;
	// protected int geometryShader;
	protected int fragmentShader;
	protected boolean initialized;

	// protected int maxOutVertCount;

	/**
	 * Creates an instance of GLSLShader.
	 * 
	 * @param scene
	 *            Scene
	 */
	public ShaderGL2(Scene scn) {
		this.scene = scn;
		this.gl = scene.pg3d.getGL().getGL2();	

		programObject = scene.createGLSLProgramObject();

		vertexShader = 0;
		// geometryShader = 0;
		fragmentShader = 0;
		initialized = false;

		// IntBuffer buf = IntBuffer.allocate(1);
		// gl.glGetIntegerv(GL2.GL_MAX_GEOMETRY_OUTPUT_VERTICES, buf);
		// maxOutVertCount = buf.get(0);
	}

	/**
	 * Creates a read-to-use instance of GLSLShader with vertex and fragment
	 * shaders
	 * 
	 * @param scene
	 *            Scene
	 * @param vertexFN
	 *            String
	 * @param fragmentFN
	 *            String
	 */
	public ShaderGL2(Scene scn, String vertexFN, String fragmentFN) {
		this(scn);
		loadVertexShader(vertexFN);
		loadFragmentShader(fragmentFN);
		setup();
	}

	protected void finalize() throws Throwable {
		try {
			if (vertexShader != 0) {
				scene.finalizeGLSLVertShaderObject(vertexShader);
			}
			if (fragmentShader != 0) {
				scene.finalizeGLSLFragShaderObject(fragmentShader);
			}
			if (programObject != 0) {
				scene.finalizeGLSLProgramObject(programObject);
			}
		} finally {
			super.finalize();
		}
	}

	/**
	 * Loads and compiles the vertex shader contained in file.
	 * 
	 * @param file
	 *            String
	 */
	public void loadVertexShader(String file) {
		String shaderSource = load(file);
		attachVertexShader(shaderSource, file);
	}

	public void loadVertexShaderSource(String source) {
		attachVertexShader(source, "");
	}

	/**
	 * Loads and compiles the fragment shader contained in file.
	 * 
	 * @param file
	 *            String
	 */
	public void loadFragmentShader(String file) {
		String shaderSource = load(file);
		attachFragmentShader(shaderSource, file);
	}

	public void loadFragmentShaderSource(String source) {
		attachFragmentShader(source, "");
	}

	public String load(String path) {
		String result = new String();
		try {
			BufferedReader shaderReader = new BufferedReader(
					new InputStreamReader(getResourceAsStream(path)));
			StringWriter shaderWriter = new StringWriter();

			String line = shaderReader.readLine();
			while (line != null) {
				shaderWriter.write(line);
				shaderWriter.write("\n");
				line = shaderReader.readLine();
			}

			result = shaderWriter.getBuffer().toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * Links the shader program and validates it.
	 */
	public void setup() {
		gl.glLinkProgram(programObject);
		gl.glValidateProgram(programObject);
		checkLogInfo("GLSL program validation: ", programObject, false);
		initialized = true;
	}

	/**
	 * Returns true or false depending on whether the shader is initialized or
	 * not.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Starts the execution of the shader program.
	 */
	public void start() {
		if (!initialized) {
			System.out.println("This shader is not properly initialized. Call the setup() method first");
		}

		// TODO:
		// set the texture uniforms to the currently values texture units for
		// all the
		// textures.
		// gl.glUniform1iARB(loc, unit);

		// parent.pg3d.getGL().getGL2().glUseProgramObjectARB(programObject);
		scene.pg3d.getGL().getGL2().glUseProgram(programObject);
	}

	/**
	 * Stops the execution of the shader program.
	 */
	public void stop() {
		scene.pg3d.getGL().getGL2().glUseProgram(0);
	}

	/**
	 * Returns the ID location of the attribute parameter given its name.
	 * 
	 * @param name
	 *            String
	 * @return int
	 */
	public int getAttribLocation(String name) {
		return (gl.glGetAttribLocation(programObject, name));
	}

	/**
	 * Returns the ID location of the uniform parameter given its name.
	 * 
	 * @param name
	 *            String
	 * @return int
	 */
	public int getUniformLocation(String name) {
		return (gl.glGetUniformLocation(programObject, name));
	}

	/**
	 * Sets the texture uniform name with the texture unit to use in the said
	 * uniform.
	 * 
	 * @param name
	 *            String
	 * @param unit
	 *            int
	 */
	public void setTexUniform(String name, int unit) {
		int loc = getUniformLocation(name);
		if (-1 < loc)
			gl.glUniform1i(loc, unit);
	}

	/**
	 * Sets the texture uniform with the unit of tex is attached to at the
	 * moment of running the shader.
	 * 
	 * @param name
	 *            String
	 * @param tex
	 *            GLTexture
	 */
	/*
	 * public void setTexUniform(String name, PTexture tex) { int loc =
	 * getUniformLocation(name); if (-1 < loc) { int tu = tex.getTextureUnit();
	 * if (-1 < tu) { // If tex is already bound to a texture unit, we use // it
	 * as the value for the uniform. gl.glUniform1iARB(loc, tu); }
	 * tex.setTexUniform(loc); } }
	 */

	/**
	 * Sets the int uniform with name to the given value
	 * 
	 * @param name
	 *            String
	 * @param x
	 *            int
	 */
	public void setIntUniform(String name, int x) {
		int loc = getUniformLocation(name);
		if (-1 < loc)
			gl.glUniform1i(loc, x);
	}

	/**
	 * Sets the float uniform with name to the given value
	 * 
	 * @param name
	 *            String
	 * @param x
	 *            float
	 */
	public void setFloatUniform(String name, float x) {
		int loc = getUniformLocation(name);
		if (-1 < loc)
			gl.glUniform1f(loc, x);
	}

	/**
	 * Sets the vec2 uniform with name to the given values.
	 * 
	 * @param nam
	 *            String
	 * @param x
	 *            float
	 * @param y
	 *            float
	 */
	public void setVecUniform(String name, float x, float y) {
		int loc = getUniformLocation(name);
		if (-1 < loc)
			gl.glUniform2f(loc, x, y);
	}
	
	// TODO needs testing
	public void setVecUniform(String name, Vector3D v) {
		int loc = getUniformLocation(name);
		if (-1 < loc)
			gl.glUniform3fv(loc, 1, v.vec , 0);
	}

	/**
	 * Sets the vec3 uniform with name to the given values.
	 * 
	 * @param name
	 *            String
	 * @param x
	 *            float
	 * @param y
	 *            float
	 * @param z
	 *            float
	 */
	public void setVecUniform(String name, float x, float y, float z) {
		int loc = getUniformLocation(name);
		if (-1 < loc)
			gl.glUniform3f(loc, x, y, z);
	}

	/**
	 * Sets the vec4 uniform with name to the given values.
	 * 
	 * @param name
	 *            String
	 * @param x
	 *            float
	 * @param y
	 *            float
	 * @param z
	 *            float
	 * @param w
	 *            float
	 */
	public void setVecUniform(String name, float x, float y, float z, float w) {
		int loc = getUniformLocation(name);
		if (-1 < loc)
			gl.glUniform4f(loc, x, y, z, w);
	}
	
	public void setMatUniform(String name, Matrix3D matrix) {
		int loc = getUniformLocation(name);
		if (-1 < loc) {
			gl.glUniformMatrix4fv(loc, 1, false, matrix.mat, 0);
			//gl.glUniformMatrix4fv(loc, 1, false, FloatBuffer.wrap( matrix.mat));// same as the previous line
		}
	}

	/**
	 * Sets the mat2 uniform with name to the given values
	 * 
	 * @param name
	 *            String
	 * @param m00
	 *            float ...
	 */
	public void setMatUniform(String name, float m00, float m01, float m10,
			float m11) {
		int loc = getUniformLocation(name);
		if (-1 < loc) {
			float[] mat = new float[4];
			mat[0] = m00;
			mat[1] = m10;
			mat[4] = m01;
			mat[5] = m11;
			gl.glUniformMatrix2fv(loc, 1, false, mat, 0);
		}
	}

	/**
	 * Sets the mat3 uniform with name to the given values
	 * 
	 * @param name
	 *            String
	 * @param m00
	 *            float ...
	 */
	public void setMatUniform(String name, float m00, float m01, float m02,
			float m10, float m11, float m12, float m20, float m21, float m22) {
		int loc = getUniformLocation(name);
		if (-1 < loc) {
			float[] mat = new float[9];
			mat[0] = m00;
			mat[1] = m10;
			mat[2] = m20;
			mat[4] = m01;
			mat[5] = m11;
			mat[6] = m21;
			mat[8] = m02;
			mat[9] = m12;
			mat[10] = m22;
			gl.glUniformMatrix3fv(loc, 1, false, mat, 0);
		}
	}

	/**
	 * Sets the mat3 uniform with name to the given values
	 * 
	 * @param name
	 *            String
	 * @param m00
	 *            float ...
	 */
	public void setMatUniform(String name, float m00, float m01, float m02,
			float m03, float m10, float m11, float m12, float m13, float m20,
			float m21, float m22, float m23, float m30, float m31, float m32,
			float m33) {
		int loc = getUniformLocation(name);
		if (-1 < loc) {
			float[] mat = new float[16];
			mat[0] = m00;
			mat[1] = m10;
			mat[2] = m20;
			mat[3] = m30;
			mat[4] = m01;
			mat[5] = m11;
			mat[6] = m21;
			mat[7] = m31;
			mat[8] = m02;
			mat[9] = m12;
			mat[10] = m22;
			mat[11] = m32;
			mat[12] = m03;
			mat[13] = m13;
			mat[14] = m23;
			mat[15] = m33;
			gl.glUniformMatrix4fv(loc, 1, false, mat, 0);
		}
	}

	/**
	 * Sets the float attribute with name to the given value
	 * 
	 * @param name
	 *            String
	 * @param x
	 *            float
	 */
	public void setFloatAttribute(String name, float x) {
		int loc = getAttribLocation(name);
		if (-1 < loc)
			gl.glVertexAttrib1f(loc, x);
	}

	/**
	 * Sets the vec2 attribute with name to the given values
	 * 
	 * @param name
	 *            String
	 * @param float x
	 * @param float y
	 */
	public void setVecAttribute(String name, float x, float y) {
		int loc = getAttribLocation(name);
		if (-1 < loc)
			gl.glVertexAttrib2f(loc, x, y);
	}

	/**
	 * Sets the vec3 attribute with name to the given values
	 * 
	 * @param name
	 *            String
	 * @param float x
	 * @param float y
	 * @param float z
	 */
	public void setVecAttribute(String name, float x, float y, float z) {
		int loc = getAttribLocation(name);
		if (-1 < loc)
			gl.glVertexAttrib3f(loc, x, y, z);
	}

	/**
	 * Sets the vec4 attribute with name to the given values
	 * 
	 * @param name
	 *            String
	 * @param float x
	 * @param float y
	 * @param float z
	 * @param float w
	 */
	public void setVecAttribute(String name, float x, float y, float z, float w) {
		int loc = getAttribLocation(name);
		if (-1 < loc)
			gl.glVertexAttrib4f(loc, x, y, z, w);
	}

	/**
	 * @param shaderSource
	 *            a string containing the shader's code
	 * @param filename
	 *            the shader's filename, used to print error log information
	 */
	private void attachVertexShader(String shaderSource, String file) {
		vertexShader = scene.createGLSLVertShaderObject();

		gl.glShaderSource(vertexShader, 1, new String[] { shaderSource },
				(int[]) null, 0);
		gl.glCompileShader(vertexShader);
		checkLogInfo("Vertex shader " + file + " compilation: ", vertexShader, true);
		scene.pg3d.getGL().getGL2().glAttachShader(programObject, vertexShader);
	}

	/**
	 * @param shaderSource
	 *            a string containing the shader's code
	 * @param filename
	 *            the shader's filename, used to print error log information
	 */
	private void attachFragmentShader(String shaderSource, String file) {
		fragmentShader = scene.createGLSLFragShaderObject();

		gl.glShaderSource(fragmentShader, 1, new String[] { shaderSource },	(int[]) null, 0);
		gl.glCompileShader(fragmentShader);
		checkLogInfo("Fragment shader " + file + " compilation: ",	fragmentShader, true);
		scene.pg3d.getGL().getGL2().glAttachShader(programObject, fragmentShader);
	}

	/**
	 * @invisible Check the log error for the opengl object obj. Prints error
	 *            message if needed.
	 */
	protected void checkLogInfo(String title, int obj, boolean isShader) {
		IntBuffer iVal = IntBuffer.allocate(1);
		
		if (isShader)
			scene.pg3d.getGL().getGL2().glGetShaderiv(obj, GL2.GL_INFO_LOG_LENGTH, iVal);
		else // is program
			scene.pg3d.getGL().getGL2().glGetProgramiv(obj, GL2.GL_INFO_LOG_LENGTH, iVal);		

		int length = iVal.get();

		if (length <= 1) {
			return;
		}

		// Some error occurred...
		ByteBuffer infoLog = ByteBuffer.allocate(length);
		iVal.flip();

		if(isShader)
			scene.pg3d.getGL().getGL2().glGetShaderInfoLog(obj, length, iVal, infoLog);
		else
			scene.pg3d.getGL().getGL2().glGetProgramInfoLog(obj, length, iVal, infoLog);

		byte[] infoBytes = new byte[length];
		infoLog.get(infoBytes);
		System.out.println(title);
		System.out.println(new String(infoBytes));
	}

	protected void release() {
		if (vertexShader != 0) {
			scene.deleteGLSLVertShaderObject(vertexShader);
			vertexShader = 0;
		}
		if (fragmentShader != 0) {
			scene.deleteGLSLFragShaderObject(fragmentShader);
			fragmentShader = 0;
		}
		if (programObject != 0) {
			scene.deleteGLSLProgramObject(programObject);
			programObject = 0;
		}
	}

	/*
	 * 
	 * 
	 * GL.GL_BOOL_ARB GL.GL_BOOL_VEC2_ARB GL.GL_BOOL_VEC3_ARB
	 * GL.GL_BOOL_VEC4_ARB GL.GL_FLOAT GL.GL_FLOAT_MAT2_ARB GL.GL_FLOAT_MAT3_ARB
	 * GL.GL_FLOAT_MAT4_ARB GL.GL_FLOAT_VEC2_ARB GL.GL_FLOAT_VEC3_ARB
	 * GL.GL_FLOAT_VEC4_ARB GL.GL_INT GL.GL_INT_VEC2_ARB GL.GL_INT_VEC3_ARB
	 * GL.GL_INT_VEC4_ARB
	 * 
	 * gl.glUseProgramObjectARB(program);
	 * 
	 * // Build a mapping from texture parameters to texture units.
	 * texUnitMap.clear(); int count, size; int type; byte[] paramName = new
	 * byte[1024];
	 * 
	 * int texUnit = 0;
	 * 
	 * int[] iVal = { 0 }; gl.glGetObjectParameterivARB(fragmentProgram,
	 * GL.GL_OBJECT_COMPILE_STATUS_ARB, iVal, 0); count = iVal[0]; for (int i =
	 * 0; i < count; ++i) { IntBuffer iVal1 = BufferUtil.newIntBuffer(1);
	 * IntBuffer iVal2 = BufferUtil.newIntBuffer(1);
	 * 
	 * ByteBuffer paramStr = BufferUtil.newByteBuffer(1024);
	 * gl.glGetActiveUniformARB(program, i, 1024, null, iVal1, iVal2, paramStr);
	 * size = iVal1.get(); type = iVal2.get(); paramStr.get(paramName);
	 * 
	 * if ((type == GL.GL_SAMPLER_1D_ARB) || (type == GL.GL_SAMPLER_2D_ARB) ||
	 * (type == GL.GL_SAMPLER_3D_ARB) || (type == GL.GL_SAMPLER_CUBE_ARB) ||
	 * (type == GL.GL_SAMPLER_1D_SHADOW_ARB) || (type ==
	 * GL.GL_SAMPLER_2D_SHADOW_ARB) || (type == GL.GL_SAMPLER_2D_RECT_ARB) ||
	 * (type == GL.GL_SAMPLER_2D_RECT_SHADOW_ARB)) { String strName = new
	 * String(paramName, 0, 1024); Integer unit = texUnit;
	 * texUnitMap.put(strName, unit); int location =
	 * gl.glGetUniformLocationARB(program, strName); gl.glUniform1iARB(location,
	 * texUnit); ++texUnit; } }
	 * 
	 * gl.glUseProgramObjectARB(0); }
	 */
	
	public static URL getResource(final String filename) throws IOException {
        // Try to load resource from jar
        URL url = ClassLoader.getSystemResource(filename);
        // If not found in jar, then load from disk
        if (url == null) {
            return new URL("file", "localhost", filename);
        } else {
            return url;
        }
    }

    public static InputStream getResourceAsStream(final String filename) throws IOException {
        // Try to load resource from jar
        InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
        // If not found in jar, then load from disk
        if (stream == null) {
            return new FileInputStream(filename);
        } else {
            return stream;
        }
    }
}
