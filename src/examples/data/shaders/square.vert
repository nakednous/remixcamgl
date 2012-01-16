uniform mat4    mgl_PMVMatrix[2];
attribute vec4    mgl_Vertex;
attribute vec4    mgl_Color;
varying   vec4    frontColor;

void main(void)
{
  frontColor=mgl_Color;
  gl_Position = mgl_PMVMatrix[0] * mgl_PMVMatrix[1] * mgl_Vertex;
}
