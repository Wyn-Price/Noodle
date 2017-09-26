package com.wynprice.noodle.noodlegenerators;

import com.wynprice.noodle.noodlegenerators.base.BaseRemovedRandomNoodle;

import net.minecraft.util.math.Vec3d;

public class StraightNoodleGenerator extends BaseRemovedRandomNoodle
{

	@Override
	protected Vec3d top3d() {
		return new Vec3d(0.1, 0.1, 0.1);
	}

	@Override
	protected Vec3d bottom3d() {
		return new Vec3d(0.1, 0.1, 0.1);
	}

}
