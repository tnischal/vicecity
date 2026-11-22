package com.example.vicecity;

/**
 * A highlight card shown on the home page.
 *
 * @param icon an emoji used as the card icon
 * @param title short card title
 * @param description one-sentence card text
 */
public record Feature(String icon, String title, String description) {}
