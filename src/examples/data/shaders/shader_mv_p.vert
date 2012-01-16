#version 150 core

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

in vec3 in_Position;
in vec3 in_Color;

out vec3 pass_Color;

void main(void)
{
    /* transform into clip space */    
    gl_Position = projectionMatrix * modelViewMatrix * vec4(in_Position, 1.0);
    pass_Color = in_Color;
}
