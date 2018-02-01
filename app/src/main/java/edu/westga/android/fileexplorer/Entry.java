package edu.westga.android.fileexplorer;

/**
 * Entry class represents files and directories contained an external storage device.
 *
 * @author Justin A.
 * @version 1.0
 */
public class Entry implements Comparable<Entry> {

    private String name;
    private String path;
    private boolean isDirectory;

    /**
     * Constructor for the entry class creates a new Entry object with a name, path, and
     * boolean value isDirectory which indicates if he entry object is a directory or a file.
     *
     * @precondition name != null
     * @precondition path != null
     *
     * @postcondition the Entry object will be initialized with a name 'name', a file path 'path',
     * and boolean value 'isDirectory'.
     *
     * @param name
     *      The name of the entry file.
     * @param path
     *      The path of the entry file.
     * @param isDirectory
     *      Determines if the entry is a file or directory.
     */
    public Entry(String name, String path, boolean isDirectory) {
        if (name == null) {
            throw new IllegalArgumentException("The nme cannot be null.");
        }
        if (path == null) {
            throw new IllegalArgumentException("The path cannot be null");
        }
        this.name = name;
        this.path = path;
        this.isDirectory = isDirectory;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    /**
     * Returns the entry file extension.
     *
     * @precondition none
     *
     * @return The entry file extension.
     */
    public String getExtension() {
        String[] nameArray = this.name.split("\\.");
        String ext = nameArray[nameArray.length - 1];
        return ext;
    }


    @Override
    public int compareTo(Entry entry) {
        return this.name.compareTo(entry.getName());
    }

}