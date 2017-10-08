package org.usfirst.frc.team1492.robot;

import java.util.ArrayList;

public class PixyCamera {

    static {
        System.loadLibrary("pixy_java");
    }

    private static boolean initialized = false;

    enum CameraSettings {
        ORANGE_LIGHT((short) 17, 250),      // Settings for orange light ring - cam_setECV(64017)
        GREEN_COMPETITION((short) 1, 80),   // Settings for green light ring at AZPX 2017 - cam_setECV(20481)
        GREEN_SHOP((short) 2, 150);         // Settings for green light ring in workshop - cam_setECV(38402)

        final short gain;
        final int compensation;

        private CameraSettings(short gain, int compensation) {
            this.gain = gain;
            this.compensation = compensation;
        }
    }


    public PixyCamera(CameraSettings cameraSettings) {
        if (!initialized) {
            if (pixy.pixy_init() != 0) {
                System.err.println("Error initializing the pixy! #####");
            }

            pixy.pixy_cam_set_auto_exposure_compensation((short) 0);
            pixy.pixy_cam_set_auto_white_balance((short) 0);

            // cam_setWBV(0x884040)
            pixy.pixy_cam_set_white_balance_value((short) 64, (short) 64, (short) 136);

            setPixySettings(cameraSettings);
            initialized = true;
        } else {
            System.err.println("Pixy already initialized.");
        }
    }

    public void setPixySettings(CameraSettings cameraSettings) {
        pixy.pixy_cam_set_exposure_compensation(cameraSettings.gain, cameraSettings.compensation);
    }

    public boolean blocksAreNew() {
        return pixy.pixy_blocks_are_new() == 1;
    }

    public int getBlocks(int maxCount, BlockArray blocks) {
        return pixy.pixy_get_blocks(maxCount, blocks);
    }

    public static Block[] pickBlocks(BlockArray blocks, int blockCount) {
        if (blockCount < 2) {
            return null;
        } else if (blockCount == 2) {
            return new Block[] {blocks.getitem(0), blocks.getitem(1)};
        } else if (blockCount > 2) {
            System.out.println("[pixy-vision] blocks over 2");

            ArrayList<Block> targets = new ArrayList<>();
            for (int i = 0; i < blockCount; i++) {
                targets.add(blocks.getitem(i));
            }

            targets.sort((Block o1, Block o2) -> Integer.compare(o1.getX(), o2.getX()));

            for (int i = 0; i < targets.size(); i++) {
                System.out.print("[pixy-vision] target " + i + ", x is " + targets.get(i).getX() + " ");
            }
            System.out.println("");
            return new Block[] {targets.get(0), targets.get(targets.size() - 1)};
        }
        return null;
    }
}
