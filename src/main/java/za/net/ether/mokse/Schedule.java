package za.net.ether.mokse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class Schedule {
    private final int code;
    private final int seed;
    private final int count;
    private final int inc;
    private final int X = 0;
    private ArrayList<ArrayList<TimeStage>> schedule;

    public Schedule(int seed, int inc, int count, int code) {
        this.seed = seed;
        this.inc = inc;
        this.count = count;
        this.code = code;
        this.schedule = getSchedule(zoneArray());

        try {
            writeListToJsonArray();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public ArrayList<ArrayList<TimeStage>> getSchedule(int[] array) {
        ArrayList<ArrayList<TimeStage>> localSchedule = new ArrayList<ArrayList<TimeStage>>();
        // for (int i = 0; i < array.length; i++) {
        // dom = (int) Math.floor(((double) i) / (31 * 96) * 31);
        // if (array[i] == code) {
        // times[dom][(int) (Math.ceil((double) ((i % 96) + 1) / (double) 8) - 1)] =
        // (int) ((8 + ((i % 96) + 1 - (Math.ceil((float) ((i % 96) + 1) / (float) 8) *
        // 8))));
        // }
        // }
        int index = 0;
        for (int i = 0; i < 31; i++) {
            ArrayList<TimeStage> day = new ArrayList<TimeStage>();
            for (int j = 0; j < 12; j++) {
                for (int k = 0; k < 8; k++) {
                    System.out.println(((j * 8) + k + (i * 96)));
                    if (array[index = (j * 8 + k + i * 96)] == code) {
                        day.add(new TimeStage((int) (Math.ceil((double) ((index % 96) + 1) / (double) 8) - 1), (int) ((8
                                + ((index % 96) + 1 - (Math.ceil((float) ((index % 96) + 1) / (float) 8) * 8))))));
                    }
                }
                localSchedule.add(day);
            }
        }
        return localSchedule;
    }

    public int[] zoneArray() {
        int[] zoneCodes = new int[31 * 12 * 8];
        boolean copy = false;
        zoneCodes[0] = seed;

        for (int i = 1; i < 4; i++) {
            if (zoneCodes[i - 1] + inc <= count) {
                zoneCodes[i] = zoneCodes[i - 1] + inc;
            } else {
                zoneCodes[i] = (zoneCodes[i - 1] + 4) % count;
            }
        }

        for (int i = 8; i < 12; i++) {
            if (zoneCodes[i - 8] != 16) {
                zoneCodes[i] = zoneCodes[i - 8] + 1;
            } else {
                if (((X * 16 + zoneCodes[i - 8]) % 48) == 0) {
                    zoneCodes[i] = (X * 16 + 1) / 48 + 2;
                } else {
                    zoneCodes[i] = 1;
                }
            }
        }

        for (int i = 12; i < 384; i++) {
            if ((i % 4 == 0) && !copy) {
                copy = true;
            } else {
                if ((i % 4 == 0) && copy) {
                    copy = false;
                }
            }
            if (copy == true) {
                zoneCodes[i] = zoneCodes[i - 12];
            } else {
                if (zoneCodes[i - 8] != count) {
                    zoneCodes[i] = zoneCodes[i - 8] + 1;
                } else {
                    zoneCodes[i] = 1;
                }
            }
        }

        for (int i = 384; i < zoneCodes.length; i++) {
            if ((i % 4 == 0) && !copy) {
                copy = true;
            } else {
                if ((i % 4 == 0) && copy) {
                    copy = false;
                }
            }
            if (copy == true) {
                zoneCodes[i] = zoneCodes[i - 12];
            } else {
                if (zoneCodes[i - 384] != count) {
                    zoneCodes[i] = zoneCodes[i - 384] + 1;
                } else {
                    zoneCodes[i] = 1;
                }
            }
        }

        for (int i = 4; i < 8; i++) {
            zoneCodes[i] = zoneCodes[2976 + (i - 12)];
        }

        return zoneCodes;
    }

    public void writeListToJsonArray() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("CustomTimeStageSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(TimeStage.class, new CustomTimeStageSerializer());
        mapper.registerModule(module);
        // Car car = new Car("yellow", "renault");
        // String carJson = mapper.writeValueAsString(car);

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper2 = new ObjectMapper();

        mapper.writeValue(out, schedule);

        final byte[] data = out.toByteArray();
        System.out.println(new String(data));
    }

    public int time(int i) {
        return (int) (Math.ceil((double) ((i % 96) + 1) / (double) 8) - 1);
    }

    public int stage(int i) {
        return (int) ((8 + ((i % 96) + 1 - (Math.ceil((double) ((i % 96) + 1) / (double) 8) * 8))));
    }
}

// public static void main(String args[]) {
// // printArr(zoneArray(1, 4, 16));
// // printTimes(zoneArray(1, 4, 16), 11);
// createArrayObj(zoneArray(1, 4, 16), 12);
// }

// public static void printArr(int[] array) {
// // for (int i = 0; i < array.length; i++) {
// // System.out.println(array[i]);
// // }
// for (int i = 0; i < 96; i++) {
// for (int j = 0; j < 31; j++) {
// System.out.print(array[i + 96 * j] + "\t");
// }
// System.out.println();
// }
// }

// public static void printTimes(int[] array, int code) {
// System.out.println("This is when you may experience loadshedding:");
// int dom = 1;
// int prevdom = 0;
// for (int i = 0; i < array.length; i++) {
// // System.out.println("DoM: " + (int) Math.floor(((double) i) / (31 * 96)*31
// +
// // 1));
// dom = (int) Math.floor(((double) i) / (31 * 96) * 31 + 1);
// if (prevdom != dom) {
// System.out.println("Day of the Month: " + dom);
// prevdom = dom;
// }
// if (array[i] == code) {
// System.out.println(String.format("%02d:00\tStage %d",
// (int) (Math.ceil((float) ((i % 96) + 1) / (float) 8) - 1) * 2,
// (int) ((8 + ((i % 96) + 1 - (Math.ceil((float) ((i % 96) + 1) / (float) 8) *
// 8))))));
// // System.out.println("i " + (i % 96));
// // System.out.println("grade: " + );
// }
// }
// }

// public static ArrayList createArrayObj(int[] array, int code) {

// }
// }