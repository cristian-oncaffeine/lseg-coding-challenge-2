package com.lseg.predict3.fileIO.dto;

import com.lseg.predict3.fileIO.dto.Directory;

import java.util.ArrayList;
import java.util.List;

public class DirectoryList {
    public List<Directory> directoryList;

    public DirectoryList() {
        directoryList = new ArrayList<Directory>();
    }
    public void add(Directory dir) {
        directoryList.add(dir);
    }
}
