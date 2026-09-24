package com.dangkhoilkb17.baritoneemergencystop;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.IBaritoneProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class BaritoneEmergencyStopClient implements ClientModInitializer {
    private static final String MOD_ID = "baritone-emergency-stop";
    private static final Identifier EMERGENCY_STOP_ID = Identifier.of(MOD_ID, "emergency_stop");
    private static final SoundEvent EMERGENCY_STOP_SOUND = Registry.register(
        Registries.SOUND_EVENT,
        EMERGENCY_STOP_ID,
        SoundEvent.of(EMERGENCY_STOP_ID)
    );
    private KeyBinding emergencyStopKey;

    @Override
    public void onInitializeClient() {
        emergencyStopKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                "key." + MOD_ID + ".emergency_stop",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F10,
                "category." + MOD_ID
            )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!emergencyStopKey.wasPressed()) {
                return;
            }

            IBaritoneProvider provider = BaritoneAPI.getProvider();
            if (provider == null) {
                return;
            }

            boolean stopped = false;
            for (IBaritone baritone : provider.getAllBaritones()) {
                if (baritone != null) {
                    stopped |= baritone.getPathingBehavior().cancelEverything();
                }
            }

            if (stopped) {
                client.getSoundManager().play(PositionedSoundInstance.master(EMERGENCY_STOP_SOUND, 1.0F));
            }
        });
    }
}
