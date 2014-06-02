/*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.blizzardtec.tsa.BusinessDelegate;
import com.blizzardtec.tsa.DelegateException;
import com.blizzardtec.tsa.data.DatasetHelper.DataType;
import com.blizzardtec.tsa.form.FileUploadForm;

/**
 * @author Barnaby Golden
 *
 */
@Controller
public final class FileUploadController extends BaseController {

    /**
     * Logger.
     */
    private static final Logger LOG =
            Logger.getLogger(FileUploadController.class);

    /**
     * Upload form name.
     */
    private static final String UPLOAD_FORM_NAME = "uploadForm";
    /**
     * Business delegate.
     */
    @Autowired
    private transient BusinessDelegate delegate;

    /**
     * Types of file to save.
     * @author Barnaby Golden
     *
     */
    private enum SaveType { STRATEGY, DATASET }

    /**
     * Show the file upload form.
     *
     * @param map map
     * @return JSP mapping
     */
    @RequestMapping(value = DATASET_UPLOAD_ACTION,
                   method = RequestMethod.GET)
    public String displayDatasetUpload(final ModelMap map) {

        populateDatasetMap(map);

        map.addAttribute(UPLOAD_FORM_NAME, new FileUploadForm());

        return DATASET_UPLOAD_PAGE;
    }

    /**
     * Populate the map for dataset.
     * @param map map
     */
    private void populateDatasetMap(final ModelMap map) {

        final ConcurrentMap<String, String> typesMap =
                    new ConcurrentHashMap<String, String>();

        for (final DataType dataType: DataType.values()) {
            typesMap.put(dataType.toString(), dataType.toString());
        }

        map.addAttribute("dataTypeValues", typesMap);
    }

    /**
     * Show the file upload form.
     *
     * @return JSP mapping
     */
    @RequestMapping(value = STRATEGY_UPLOAD_ACTION,
                   method = RequestMethod.GET)
    public String displayStrategyUpload() {

        return STRATEGY_UPLOAD_PAGE;
    }

    /**
     * Save the uploaded dataset file.
     *
     * @param uploadForm upload form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(value = DATASET_SAVE_ACTION,
                   method = RequestMethod.POST)
    public String saveDataset(
       @Valid @ModelAttribute(UPLOAD_FORM_NAME) final FileUploadForm uploadForm,
       final BindingResult result, final ModelMap map) {

        String retVal = null;

        if (result.hasErrors()) {
            populateDatasetMap(map);
            retVal = DATASET_UPLOAD_PAGE;
        } else {
            retVal = processFileSave(uploadForm, result, SaveType.DATASET);
        }

        return retVal;
    }

    /**
     * Save the uploaded dataset file.
     *
     * @param uploadForm upload form
     * @param map map
     * @param result error handler
     * @return JSP mapping
     */
    @RequestMapping(value = STRATEGY_SAVE_ACTION,
                   method = RequestMethod.POST)
    public String saveStrategy(
       @Valid @ModelAttribute(UPLOAD_FORM_NAME) final FileUploadForm uploadForm,
       final BindingResult result, final Model map) {

        String retVal = null;

        if (result.hasErrors()) {
            retVal = STRATEGY_UPLOAD_PAGE;
        } else {
            retVal = processFileSave(uploadForm, result, SaveType.STRATEGY);
        }

        return retVal;
    }

    /**
     * Load the file and report any errors.
     * @param uploadForm form
     * @param result result object
     * @param type the type of file being processed
     * @return JSP mapping
     */
    private String processFileSave(
            final FileUploadForm uploadForm,
            final BindingResult result,
            final SaveType type) {

        final String param = "listType=" + type.name();
        String retVal = redirect(LIST_ACTION, new String[] {param});

        final List<MultipartFile> files = uploadForm.getFiles();
        final List<String> names = uploadForm.getNames();

        // during testing we use the filePath text input as this is legal
        // on browsers and avoids having to use a robot to press the browse
        // button and navigate through the browser/os specific file
        // upload dialog
        final String filePath = uploadForm.getFilePath();

        InputStream stream = null;

        try {

            if (filePath != null && filePath.length() > 0) {

                final File file = new File(filePath);

                stream = new FileInputStream(file);

            } else if (files != null && !files.isEmpty()) {

                stream = files.get(0).getInputStream();
            }

        } catch (FileNotFoundException fe) {

            result.reject("FileNotFoundError");
            retVal = DATASET_UPLOAD_PAGE;
            LOG.error(fe.getMessage());

        } catch (IOException ioe) {

            result.reject("IOError");
            retVal = DATASET_UPLOAD_PAGE;
            LOG.error(ioe.getMessage());

        }

        try {
            if (type == SaveType.STRATEGY) {
                delegate.saveStrategy(stream, names.get(0));
            } else if (type == SaveType.DATASET) {
                delegate.saveDataSet(
                        stream,
                        names.get(0),
                        DataType.valueOf(uploadForm.getDataType()));
            }
        } catch (DelegateException de) {
            if (type == SaveType.STRATEGY) {
                result.reject("StrategySaveError");
            } else if (type == SaveType.DATASET) {
                result.reject("DatasetSaveError");
            }

            if (type == SaveType.STRATEGY) {
                retVal = STRATEGY_UPLOAD_PAGE;
            } else if (type == SaveType.DATASET) {
                retVal = DATASET_UPLOAD_PAGE;
            }

            LOG.error(de.getMessage());
        }

        return retVal;
    }
}
