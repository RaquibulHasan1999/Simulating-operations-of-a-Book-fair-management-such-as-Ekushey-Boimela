package com.cse213project.bookfair.Raquibul.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BinaryFileHelper<E> {
    private String fileName;

    public BinaryFileHelper(String fileName) {
        this.fileName = fileName;
    }

    public boolean fileExists() {
        File f = new File(this.fileName);
        return f.exists();
    }

    public void appendObject(E obj) {
        File f = new File(this.fileName);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if (f.exists()) {
                fos = new FileOutputStream(f, true);
                oos = new AppendableObjectOutputStream(fos);
            } else {
                fos = new FileOutputStream(f);
                oos = new ObjectOutputStream(fos);
            }
            oos.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Error writing to " + this.fileName + ": " + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing " + this.fileName);
            }
        }
    }

    public void overwriteAll(ArrayList<E> list) {
        File f = new File(this.fileName);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            for (E item : list) {
                oos.writeObject(item);
            }
        } catch (IOException e) {
            System.out.println("Error overwriting " + this.fileName + ": " + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing " + this.fileName);
            }
        }
    }

    public ArrayList<E> readAll() {
        ArrayList<E> list = new ArrayList<E>();
        File f = new File(this.fileName);
        if (!f.exists()) {
            return list;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            while (true) {
                E obj = (E) ois.readObject();
                list.add(obj);
            }
        } catch (EOFException e) {
        } catch (IOException e) {
            System.out.println("Error reading " + this.fileName + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found in " + this.fileName + ": " + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing " + this.fileName);
            }
        }
        return list;
    }
}
