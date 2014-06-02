 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Barnaby Golden
 *
 */
public final class FileUploadForm {

    /**
     * List of uploaded files.
     */
    private List<MultipartFile> files;
    /**
     * List of names for the uploaded data sets.
     */
    private List<String> names;
    /**
     * Path to the file to upload.
     * Used mainly for testing purposes.
     */
    private String filePath;
    /**
     * The type of data being uploaded.
     */
    private String dataType;

    /**
     * @return the files
     */
    public List<MultipartFile> getFiles() {
        return files;
    }

    /**
     * @return the datasetNames
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(final List<String> names) {
        this.names = names;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(final List<MultipartFile> files) {
        this.files = files;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }
}
