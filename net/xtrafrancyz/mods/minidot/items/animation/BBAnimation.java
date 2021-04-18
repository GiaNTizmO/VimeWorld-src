package net.xtrafrancyz.mods.minidot.items.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.ToDoubleFunction;
import net.xtrafrancyz.mods.minidot.MiniDot;
import net.xtrafrancyz.mods.minidot.items.BaseItem;
import net.xtrafrancyz.mods.minidot.items.MModelRenderer;
import org.apache.logging.log4j.Level;

public class BBAnimation implements IAnimation
{
    private final String name;
    private final boolean loop;
    private final List<BBAnimation.Bone> bones;
    private float duration;
    private float startTime = -1.0F;
    private float nextPlay;

    public BBAnimation(String name, JsonObject animation)
    {
        this.name = name;
        this.loop = animation.has("loop") && animation.getAsJsonPrimitive("loop").getAsBoolean();
        this.bones = new ArrayList();

        for (Entry<String, JsonElement> entry : animation.getAsJsonObject("bones").entrySet())
        {
            List<BBAnimation.KeyFrameSequense> list = new ArrayList();

            for (Entry<String, JsonElement> entry1 : ((JsonElement)entry.getValue()).getAsJsonObject().entrySet())
            {
                BBAnimation.KeyFrameSequense bbanimation$keyframesequense = new BBAnimation.KeyFrameSequense(BBAnimation.Channel.valueOf(((String)entry1.getKey()).toUpperCase(Locale.US)));
                list.add(bbanimation$keyframesequense);
                JsonElement jsonelement = (JsonElement)entry1.getValue();

                if (jsonelement.isJsonObject())
                {
                    for (Entry<String, JsonElement> entry2 : jsonelement.getAsJsonObject().entrySet())
                    {
                        BBAnimation.KeyFrame bbanimation$keyframe1 = new BBAnimation.KeyFrame();
                        bbanimation$keyframe1.time = Float.parseFloat((String)entry2.getKey());
                        this.duration = Math.max(bbanimation$keyframe1.time, this.duration);
                        JsonElement jsonelement1 = (JsonElement)entry2.getValue();

                        if (jsonelement1.isJsonObject())
                        {
                            JsonObject jsonobject = jsonelement1.getAsJsonObject();

                            if (jsonobject.has("lerp_mode"))
                            {
                                bbanimation$keyframe1.lerp = jsonobject.get("lerp_mode").getAsString();
                            }

                            bbanimation$keyframe1.post = new BBAnimation.Point(jsonobject.get("post"));
                            bbanimation$keyframe1.pre = jsonobject.has("pre") ? new BBAnimation.Point(jsonobject.get("pre")) : bbanimation$keyframe1.post;
                        }
                        else
                        {
                            bbanimation$keyframe1.pre = bbanimation$keyframe1.post = new BBAnimation.Point(jsonelement1);
                        }

                        bbanimation$keyframesequense.frames.add(bbanimation$keyframe1);
                    }

                    bbanimation$keyframesequense.frames.sort(Comparator.<BBAnimation.KeyFrame>comparingDouble((f) ->
                    {
                        return (double)f.time;
                    }));
                }
                else
                {
                    BBAnimation.KeyFrame bbanimation$keyframe = new BBAnimation.KeyFrame();
                    bbanimation$keyframe.pre = bbanimation$keyframe.post = new BBAnimation.Point(jsonelement);
                    bbanimation$keyframesequense.frames.add(bbanimation$keyframe);
                }
            }

            this.bones.add(new BBAnimation.Bone((String)entry.getKey(), list));
        }
    }

    public IAnimation bind(BaseItem item)
    {
        for (BBAnimation.Bone bbanimation$bone : this.bones)
        {
            if (bbanimation$bone.renderer == null)
            {
                bbanimation$bone.renderer = findRenderer(bbanimation$bone.name, item.getAllModels());

                if (bbanimation$bone.renderer == null)
                {
                    MiniDot.log.log(Level.WARN, "Unable to find bone \'" + bbanimation$bone.name + "\' for the animation \'" + this.name + "\' in " + item.getClass().getName());
                    bbanimation$bone.renderer = new MModelRenderer(item);
                }

                bbanimation$bone.renderer.setDynamic(true);
            }
        }

        item.recollectModels();
        return this;
    }

    private static MModelRenderer findRenderer(String name, MModelRenderer[] renderers)
    {
        for (MModelRenderer mmodelrenderer : renderers)
        {
            if (mmodelrenderer.name.equals(name))
            {
                return mmodelrenderer;
            }

            if (mmodelrenderer.childModels != null)
            {
                MModelRenderer[] ammodelrenderer = new MModelRenderer[mmodelrenderer.childModels.size()];
                int int = 0;

                for (Object object : mmodelrenderer.childModels)
                {
                    ammodelrenderer[int++] = (MModelRenderer)object;
                }

                MModelRenderer mmodelrenderer1 = findRenderer(name, ammodelrenderer);

                if (mmodelrenderer1 != null)
                {
                    return mmodelrenderer1;
                }
            }
        }

        return null;
    }

    public void reset()
    {
        this.startTime = -1.0F;
    }

    public boolean isFinished()
    {
        return this.startTime == -2.0F;
    }

    public void tickDelayed(float time, float delaySeconds)
    {
        if (this.nextPlay <= time)
        {
            this.reset();
            this.nextPlay = time + (delaySeconds + this.duration) * 20.0F;
        }

        if (!this.isFinished())
        {
            this.tick(time);
        }

        this.tick(time);
    }

    public void tick(float time)
    {
        time = time / 20.0F;

        if (this.startTime == -1.0F)
        {
            this.startTime = time;
        }

        float float = time - this.startTime;

        if (this.loop)
        {
            if (float > this.duration)
            {
                if (float / this.duration > 100.0F)
                {
                    this.startTime = time;
                    float = 0.0F;
                }
                else
                {
                    while (float > this.duration)
                    {
                        float -= this.duration;
                        this.startTime += this.duration;
                    }
                }
            }
        }
        else
        {
            if (this.isFinished())
            {
                return;
            }

            if (float > this.duration)
            {
                float = this.duration;
            }
        }

        for (BBAnimation.Bone bbanimation$bone : this.bones)
        {
            for (BBAnimation.KeyFrameSequense bbanimation$keyframesequense : bbanimation$bone.channels)
            {
                boolean boolean = false;

                for (int int = 0; int < bbanimation$keyframesequense.frames.size(); ++int)
                {
                    BBAnimation.KeyFrame bbanimation$keyframe = (BBAnimation.KeyFrame)bbanimation$keyframesequense.frames.get(int);

                    if (bbanimation$keyframe.time > float)
                    {
                        if (int == 0)
                        {
                            apply(bbanimation$bone, bbanimation$keyframesequense.channel, (BBAnimation.KeyFrame)null, bbanimation$keyframe, 1.0F);
                        }
                        else
                        {
                            BBAnimation.KeyFrame bbanimation$keyframe1 = (BBAnimation.KeyFrame)bbanimation$keyframesequense.frames.get(int - 1);
                            apply(bbanimation$bone, bbanimation$keyframesequense.channel, bbanimation$keyframe1, bbanimation$keyframe, (float - bbanimation$keyframe1.time) / (bbanimation$keyframe.time - bbanimation$keyframe1.time));
                        }

                        boolean = true;
                        break;
                    }
                }

                if (!boolean)
                {
                    BBAnimation.KeyFrame bbanimation$keyframe2 = (BBAnimation.KeyFrame)bbanimation$keyframesequense.frames.get(bbanimation$keyframesequense.frames.size() - 1);
                    apply(bbanimation$bone, bbanimation$keyframesequense.channel, bbanimation$keyframe2, (BBAnimation.KeyFrame)null, 0.0F);
                }
            }
        }

        if (!this.loop && float == this.duration)
        {
            this.startTime = -2.0F;
        }
    }

    private static void apply(BBAnimation.Bone bone, BBAnimation.Channel channel, BBAnimation.KeyFrame before, BBAnimation.KeyFrame after, float progress)
    {
        BBAnimation.Point bbanimation$point = null;

        if (progress == 1.0F)
        {
            bbanimation$point = after.pre;
        }
        else if (progress == 0.0F)
        {
            bbanimation$point = before.post;
        }

        if (bbanimation$point != null)
        {
            switch (channel)
            {
                case POSITION:
                    bone.renderer.rotationPointX = bone.renderer.origRotationPointX + bbanimation$point.x;
                    bone.renderer.rotationPointY = bone.renderer.origRotationPointY - bbanimation$point.y;
                    bone.renderer.rotationPointZ = bone.renderer.origRotationPointZ + bbanimation$point.z;
                    break;

                case ROTATION:
                    bone.renderer.rotationX = 0.017453292F * bbanimation$point.x;
                    bone.renderer.rotationY = 0.017453292F * bbanimation$point.y;
                    bone.renderer.rotationZ = 0.017453292F * bbanimation$point.z;
                    break;

                case SCALE:
                    bone.renderer.scaleX = bbanimation$point.x;
                    bone.renderer.scaleY = bbanimation$point.y;
                    bone.renderer.scaleZ = bbanimation$point.z;
            }
        }
        else
        {
            switch (channel)
            {
                case POSITION:
                    bone.renderer.rotationPointX = bone.renderer.origRotationPointX + lerp(before.post.x, after.pre.x, progress);
                    bone.renderer.rotationPointY = bone.renderer.origRotationPointY - lerp(before.post.y, after.pre.y, progress);
                    bone.renderer.rotationPointZ = bone.renderer.origRotationPointZ + lerp(before.post.z, after.pre.z, progress);
                    break;

                case ROTATION:
                    bone.renderer.rotationX = 0.017453292F * lerp(before.post.x, after.pre.x, progress);
                    bone.renderer.rotationY = 0.017453292F * lerp(before.post.y, after.pre.y, progress);
                    bone.renderer.rotationZ = 0.017453292F * lerp(before.post.z, after.pre.z, progress);
                    break;

                case SCALE:
                    bone.renderer.scaleX = lerp(before.post.x, after.pre.x, progress);
                    bone.renderer.scaleY = lerp(before.post.y, after.pre.y, progress);
                    bone.renderer.scaleZ = lerp(before.post.z, after.pre.z, progress);
            }
        }
    }

    private static float lerp(float before, float after, float progress)
    {
        return before + (after - before) * progress;
    }

    private static class Bone
    {
        String name;
        MModelRenderer renderer;
        List<BBAnimation.KeyFrameSequense> channels;

        public Bone(String name, List<BBAnimation.KeyFrameSequense> channels)
        {
            this.name = name;
            this.channels = channels;
        }
    }

    private static enum Channel
    {
        POSITION,
        ROTATION,
        SCALE;
    }

    private static class KeyFrame
    {
        float time;
        BBAnimation.Point pre;
        BBAnimation.Point post;
        String lerp;

        private KeyFrame()
        {
            this.lerp = "linear";
        }
    }

    private static class KeyFrameSequense
    {
        BBAnimation.Channel channel;
        List<BBAnimation.KeyFrame> frames;

        public KeyFrameSequense(BBAnimation.Channel channel)
        {
            this.channel = channel;
            this.frames = new ArrayList();
        }
    }

    private static class Point
    {
        float x;
        float y;
        float z;

        private Point(JsonElement value)
        {
            if (value.isJsonPrimitive())
            {
                this.x = this.y = this.z = value.getAsFloat();
            }

            if (value.isJsonArray())
            {
                JsonArray jsonarray = value.getAsJsonArray();

                if (jsonarray.size() == 1)
                {
                    this.x = this.y = this.z = jsonarray.get(0).getAsFloat();
                }
                else
                {
                    this.x = jsonarray.get(0).getAsFloat();
                    this.y = jsonarray.get(1).getAsFloat();
                    this.z = jsonarray.get(2).getAsFloat();
                }
            }
        }
    }
}
