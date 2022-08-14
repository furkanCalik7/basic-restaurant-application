package restaurant;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is responsible for save and load the restaurant data. Restaurant class and other classes are serializable so that
 * their states can be stored in the restaurant_data file.
 */
public class RestaurantStream {
    public static void save(Restaurant restaurant) {
        try (FileOutputStream fos = new FileOutputStream("restaurant_data");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Restaurant load() {
        try (FileInputStream fis = new FileInputStream("restaurant_data");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (Restaurant) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
