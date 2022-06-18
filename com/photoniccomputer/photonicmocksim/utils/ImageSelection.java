package com.photoniccomputer.photonicmocksim.utils;

//Listing 9-3. Enhancing ImageSelection
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
import java.io.*;
import java.lang.Object.*;
//import com.sun.image.codec.jpeg.*;


public class ImageSelection implements Transferable, ClipboardOwner{//implements Transferable

    private static ImageData imageData;

    public final static DataFlavor IMAGE_DATA_FLAVOR = new DataFlavor (ImageData.class, "Image Data");

    public final static DataFlavor JPEG_MIME_FLAVOR = new DataFlavor ("image/jpeg", "JPEG Image Data");

    private final static DataFlavor [] flavors = {
        JPEG_MIME_FLAVOR, IMAGE_DATA_FLAVOR
    };

    public ImageSelection(ImageData data) {
        imageData = data;
    }

    public Object getTransferData(DataFlavor flavor) throws java.io.IOException, UnsupportedFlavorException {
        if (flavor.equals(IMAGE_DATA_FLAVOR)) {
            return imageData;
        } else if (flavor.equals(JPEG_MIME_FLAVOR)) {
            //return getJPEGInputStream();
            return imageData;
        }
        throw new UnsupportedFlavorException(flavor);
    }

    public static ImageData getImageData(){
         return imageData;
    }

    public DataFlavor [] getTransferDataFlavors() {
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (int i = 0; i < flavors.length; i++) {
            if (flavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }

    public void lostOwnership(Clipboard cb, Transferable t) {}

}