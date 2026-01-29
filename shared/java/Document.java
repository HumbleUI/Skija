package io.github.humbleui.skija;

import io.github.humbleui.skija.impl.*;
import org.jetbrains.annotations.*;

public class Document extends RefCnt {
    static { Library.staticLoad(); }
    
    public Document(long ptr) {
        super(ptr);
    }

    /**
     * <p>Create a PDF-backed document, writing the results into a WStream.</p>
     *
     * <p>PDF pages are sized when they are created, via beginPage().</p>
     *
     * @param stream  Where the PDF is written
     * @return        PDF Document
     */
    public static Document makePDF(WStream stream) {
        return makePDF(stream, null, null, null, null, null, null, 0, 0);
    }
    
    /**
     * <p>Create a PDF-backed document, writing the results into a WStream.</p>
     *
     * <p>PDF pages are sized when they are created, via beginPage().</p>
     *
     * @param stream        Where the PDF is written
     * @param title         Title of the document.
     * @param author        Author of the document.
     * @param subject       Subject of the document.
     * @param keywords      Keywords for the document.
     * @param creator       Creator of the document.
     * @param producer      Producer of the document.
     * @param creationDate  Creation date of the document.
     * @param modifiedDate  Modification date of the document.
     * @return              PDF Document
     */
    public static Document makePDF(WStream stream, String title, String author, String subject, String keywords, String creator, String producer, long creationDate, long modifiedDate) {
        Stats.onNativeCall();
        return new Document(_nMakePDF(stream._ptr, title, author, subject, keywords, creator, producer, creationDate, modifiedDate));
    }

    /**
     * <p>Begin a new page for the document, returning the canvas for that page.
     * The canvas is owned by the document, and must not be used after endPage()
     * or close() is called.</p>
     *
     * @param width   width of the page
     * @param height  height of the page
     * @return        Canvas for the page
     */
    public Canvas beginPage(float width, float height) {
        Stats.onNativeCall();
        return new Canvas(_nBeginPage(_ptr, width, height), false, this);
    }

    /**
     * <p>Call endPage() when the content for the current page has been drawn
     * (into the canvas returned by beginPage()).</p>
     */
    public void endPage() {
        Stats.onNativeCall();
        _nEndPage(_ptr);
    }

    @Override
    public void close() {
        if (_ptr == 0) return;
        Stats.onNativeCall();
        _nClose(_ptr);
        super.close();
    }

    /**
     * <p>Call abort() to stop writing the document and discard the output.</p>
     */
    public void abort() {
        Stats.onNativeCall();
        _nAbort(_ptr);
    }

    @ApiStatus.Internal public static native long _nMakePDF(long streamPtr, String title, String author, String subject, String keywords, String creator, String producer, long creationDate, long modifiedDate);
    @ApiStatus.Internal public static native long _nBeginPage(long ptr, float width, float height);
    @ApiStatus.Internal public static native void _nEndPage(long ptr);
    @ApiStatus.Internal public static native void _nClose(long ptr);
    @ApiStatus.Internal public static native void _nAbort(long ptr);
}
