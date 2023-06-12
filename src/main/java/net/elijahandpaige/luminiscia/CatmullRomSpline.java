package net.elijahandpaige.luminiscia;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;

public class CatmullRomSpline {
    // https://github.com/Aeroluna/Heck/blob/c2862585bf78aaa83c97007d5d8fea7ae96c6a86/Heck/Animation/PointDefinitionExtensions.cs#L240-L259
    // thanks aero heart emoji

    public static Vec3i getPoint(ArrayList<Vec3i> points, float position) {
        float leftPoint = position * (points.size() - 1);
        int p1Index = MathHelper.floor(leftPoint);
        int p0Index = Math.max(p1Index - 1, 0);
        int p2Index = Math.min(p1Index + 1, points.size() - 1);
        int p3Index = Math.min(p1Index + 2, points.size() - 1);

        Vec3i p0 = points.get(p0Index);
        Vec3i p1 = points.get(p1Index);
        Vec3i p2 = points.get(p2Index);
        Vec3i p3 = points.get(p3Index);

        float t = leftPoint % 1;
        float tt = t * t;
        float ttt = tt * t;

        float q0 = -ttt + (2f * tt) - t;
        float q1 = (3f * ttt) - (5f * tt) + 2f;
        float q2 = (-3f * ttt) + (4f * tt) + t;
        float q3 = ttt - tt;

        return new Vec3i(
                Math.round(0.5f * ((p0.getX() * q0) + (p1.getX() * q1) + (p2.getX() * q2) + (p3.getX() * q3))),
                Math.round(0.5f * ((p0.getY() * q0) + (p1.getY() * q1) + (p2.getY() * q2) + (p3.getY() * q3))),
                Math.round(0.5f * ((p0.getZ() * q0) + (p1.getZ() * q1) + (p2.getZ() * q2) + (p3.getZ() * q3)))
        );
    }
}
