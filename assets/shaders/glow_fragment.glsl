#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float glowAmount;  // You can control the amount of glow with this

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);

    // Basic bloom effect
    float glow = glowAmount * (texColor.r + texColor.g + texColor.b);
    vec4 glowColor = vec4(glow, glow, glow, texColor.a);

    // Combine the original color with the glow
    gl_FragColor = mix(texColor, glowColor, 0.5) * v_color;
}