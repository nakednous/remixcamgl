package examples.basic_gl4_p_mv;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;

//import javax.media.opengl.GL2;
import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.common.nio.Buffers;
//import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import remixlab.remixcamgl.*;
import remixlab.remixcamgl.gl4.ShaderGL4;

import remixlab.remixcam.core.InteractiveFrame;
import remixlab.remixcam.geom.*;

public class SimpleSceneGL4 extends Scene {	
	ShaderGL4 shader;	
	int[] vaoID = new int[1];
	int[] vboID = new int[2];	
	private FloatBuffer cubeVertices;
	private FloatBuffer cubeColors;
	
	private static final float[] s_squareVertices =
        {
		-0.5f, -0.5f, 0.0f, // Bottom left corner
	    -0.5f, 0.5f, 0.0f, // Top left corner
	    0.5f, 0.5f, 0.0f, // Top Right corner
	    0.5f, -0.5f, 0.0f, // Bottom right corner
	    -0.5f, -0.5f, 0.0f, // Bottom left corner
	    0.5f, 0.5f, 0.0f, // Top Right corner 
        };

	private static final float[] s_squareColors =
        {
		1.0f, 1.0f, 1.0f, // Bottom left corner
	    1.0f, 0.0f, 0.0f, // Top left corner
	    0.0f, 1.0f, 0.0f, // Top Right corner
	    0.0f, 0.0f, 1.0f, // Bottom right corner
	    1.0f, 1.0f, 1.0f, // Bottom left corner
	    0.0f, 1.0f, 0.0f, // Top Right corner 
        };

    public SimpleSceneGL4(GLAutoDrawable renderer) {
		super(renderer);		
	}

    public static void main(String[] args) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);        

        Frame frame = new Frame("remixcam - AWT Window Test");
        frame.setSize(300, 300);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });   
        
        SimpleSceneGL4 scene = new SimpleSceneGL4(canvas);
        canvas.addGLEventListener(scene);   
        scene.registerComponent(canvas);//hack: should it be added automatically?
                
        // /**
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.add(canvas);
        animator.start();
        // */
    }    
     
    @Override
    public void init() {    	
    	GL4 gl = this.renderer().getGL().getGL4();
              
        // OpenGL Render Settings
        
        // Set color and depth clear value
	    gl.glClearDepth(1.f);
	    //gl.glClearColor(0.f, 0.f, 0.f, 0.f);
	    gl.glClearColor(0.4f, 0.6f, 0.9f, 0.0f);

	    // Enable Z-buffer read and write
	    gl.glEnable(GL4.GL_DEPTH_TEST);
	    gl.glDepthMask(true);
	    gl.glDisable(GL4.GL_BLEND);
        
        setRadius(1);
        showAll();
        
        // A Scene has a single InteractiveFrame (null by default). We set it here.
        setInteractiveFrame(new InteractiveFrame(this));
        interactiveFrame().translate(new Vector3D(.3f, .3f, .3f));
        // press 'i' to switch the interaction between the camera frame and the interactive frame
        setShortcut('i', Scene.KeyboardAction.FOCUS_INTERACTIVE_FRAME);
        // press 'f' to display frame selection hints
        setShortcut('f', Scene.KeyboardAction.DRAW_FRAME_SELECTION_HINT);
        
        createSquare(gl);
                
        shader = new ShaderGL4(this, "/home/pierre/workspace/remixcamgl/src/examples/data/shaders/shader_mv_p.vert",
        		                     "/home/pierre/workspace/remixcamgl/src/examples/data/shaders/shader.frag");        
    }  
  
    private void createSquare(GL4 gl) {
    	this.cubeVertices = Buffers.newDirectFloatBuffer(s_squareVertices.length);
        cubeVertices.put(s_squareVertices);
        cubeVertices.flip();
        
        this.cubeColors = Buffers.newDirectFloatBuffer(s_squareColors.length);
        cubeColors.put(s_squareColors);
        cubeColors.flip();        

        gl.glGenVertexArrays(1, vaoID, 0); // Create our Vertex Array Object
        gl.glBindVertexArray(vaoID[0]); // Bind our Vertex Array Object so we can use it        
                
        gl.glGenBuffers(2, vboID, 0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vboID[0]); // Bind our Vertex Buffer Object        
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, cubeVertices.limit() * Buffers.SIZEOF_FLOAT, cubeVertices, GL4.GL_STATIC_DRAW);        
        gl.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, 0, 0); // Set up our vertex attributes pointer      

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vboID[1]); // Bind our second Vertex Buffer Object        
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, cubeColors.limit() * Buffers.SIZEOF_FLOAT, cubeColors, GL4.GL_STATIC_DRAW);        
        gl.glVertexAttribPointer(1, 3, GL4.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1); // Enable the second vertex attribute array

        gl.glEnableVertexAttribArray(0); // Disable our Vertex Array Object
        gl.glBindVertexArray(0); // Disable our Vertex Buffer Object	
	}   
	
    @Override
    public void proscenium() {
    	GL4 gl = this.renderer().getGL().getGL4();
    	gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);    	    	
    	    	
    	shader.start(); // i.e., glUseProgram    	    	
    	
    	/**
    	matrixMode(PROJECTION);    	
    	shader.setMatUniform("projectionMatrix", getMatrix());
    	matrixMode(MODELVIEW);
    	shader.setMatUniform("modelViewMatrix", getModelViewMatrix());
    	// */
    	// Shader.setMatUniform() does: glGetUniformLocation and glUniformMatrix4fv for us :)
    	// Same as the previous commented lines
    	shader.setMatUniform("projectionMatrix", getProjectionMatrix());
    	shader.setMatUniform("modelViewMatrix", getModelViewMatrix());
    	    	
    	gl.glBindVertexArray(vaoID[0]); // Bind our Vertex Array Object
    	gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 6); // Draw our square    	
    	
    	// Save the current model view matrix
    	pushMatrix();
    	// Multiply matrix to get in the frame coordinate system.
    	//applyMatrix(interactiveFrame().matrix());
    	interactiveFrame().applyTransformation(); //exactly the same as the previous line
    	// draw a second square
    	/**
    	matrixMode(PROJECTION);
    	shader.setMatUniform("projectionMatrix", getMatrix());    	
    	matrixMode(MODELVIEW);
    	shader.setMatUniform("modelViewMatrix", getModelViewMatrix());
    	*/    	
    	// Same as the previous commented lines
    	shader.setMatUniform("projectionMatrix", getProjectionMatrix());
    	shader.setMatUniform("modelViewMatrix", getModelViewMatrix());
    	
    	gl.glBindVertexArray(vaoID[0]); // Bind our Vertex Array Object
    	gl.glDrawArrays(GL4.GL_TRIANGLES, 0, 6); // Draw our square    			
    	popMatrix();
    	
    	gl.glBindVertexArray(0); // Unbind our Vertex Array Object
        
    	shader.stop(); //gl.glUseProgram(0);
    }    
}
