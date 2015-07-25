package org.contextio.common;

/**
 * Supported filters for searching on email messages Comments on values were
 * extracted from ContextIO REST documentation {@link https
 * ://context.io/docs/2.0/accounts/messages}
 * 
 * @author David Gamez
 *
 */
@SuppressWarnings("rawtypes")
public enum EmailMessageFilter {

    /**
     * Get messages whose subject matches this search string. To use
     */
    subject,

    /**
     * Email address or top level domain of the contact for whom you want the
     * latest messages exchanged with. By "exchanged with contact X" we mean any
     * email received from contact X, sent to contact X or sent by anyone to
     * both contact X and the source owner.
     */
    email,

    /**
     * Email address of a contact messages have been sent to.
     */
    to,

    /**
     * Email address of a contact messages have been received from.
     */
    from,

    /**
     * Email address of a contact CC'ed on the messages.
     */
    cc,

    /**
     * Email address of a contact BCC'ed on the messages.
     */
    bcc,

    /**
     * Filter messages by the folder (or Gmail label). This parameter can be the
     * complete folder name with the appropriate hierarchy delimiter for the
     * mail server being queried (eg. Inbox/My folder) or the "symbolic name" of
     * the folder (eg. \Starred). The symbolic name refers to attributes used to
     * refer to special use folders in a language-independent way. See RFC-6154.
     */
    folder,

    /**
     * Filter messages by the account source label.
     */
    source,

    /**
     * Search for files based on their name. You can filter names using typical
     * shell wildcards such as *, ? and [] or regular expressions by enclosing
     * the search expression in a leading / and trailing /. For example, *.pdf
     * would give you all PDF files while /\.jpe?g$/ would return all files
     * whose name ends with .jpg or .jpeg
     */
    filename(String.class, "file_name"),

    /**
     * Search for files based on their size (in bytes).
     */
    fileSizeMin(Integer.class, "file_size_min"),

    /**
     * Search for files based on their size (in bytes).
     */
    fileSizeMax(Integer.class, "file_size_max"),

    /**
     * (unix time) Only include messages before a given timestamp. The value
     * this filter is applied to is the Date: header of the message which refers
     * to the time the message is sent from the origin.
     */
    dateBefore(Integer.class, "date_before"),

    /**
     * (unix time) Only include messages after a given timestamp. The value this
     * filter is applied to is the Date: header of the message which refers to
     * the time the message is sent from the origin.
     */
    dateAfter(Integer.class, "date_after"),

    /**
     * (unix time) Only include messages indexed before a given timestamp. This
     * is not the same as the date of the email, it is the time Context.IO
     * indexed this message.
     */
    indexedBefore(Integer.class, "indexed_before"),

    /**
     * (unix time) Only include messages indexed after a given timestamp. This
     * is not the same as the date of the email, it is the time Context.IO
     * indexed this message.
     */
    indexedAfter(Integer.class, "indexed_after"),

    /**
     * Set to 1 to include thread size in the result.
     */
    includeThreadSize(Integer.class, "include_thread_size"),

    /**
     * Set to 1 to include message bodies in the result. Since message bodies
     * must be retrieved from the IMAP server, expect a performance hit when
     * setting this parameter.
     */
    includeBody(Integer.class, "include_body"),

    /**
     * Can be set to 0 (default), 1 or raw. If set to 1, complete message
     * headers, parsed into an array, are included in the results. If set to
     * raw, the headers are also included but as a raw unparsed string. Since
     * full original headers bodies must be retrieved from the IMAP server,
     * expect a performance hit when setting this parameter.
     */
    includeHeaders(Integer.class, "include_headers"),

    /**
     * Set to 1 to include IMAP flags of messages in the result. Since message
     * flags must be retrieved from the IMAP server, expect a performance hit
     * when setting this parameter.
     */
    includeFlags(Integer.class, "include_flags"),

    /**
     * Used when include_body is set to get only body parts of a given MIME-type
     * (for example text/html)
     */
    bodyType(String.class, "body_type"),

    /**
     * Set to 1 to include message sources in the result. Since message sources
     * must be retrieved from the IMAP server, expect a performance hit when
     * setting this parameter.
     */
    includeSource(Integer.class, "include_source"),

    /**
     * The sort order of the returned results. Possible values are asc and desc
     */
    sortOrder(String.class, "sort_order"),

    /**
     * The maximum number of results to return. The maximum limit is 100.
     */
    limit(Integer.class),

    /**
     * Start the list at this offset (zero-based).
     */
    offset(Integer.class);

    private Class filterClass;
    private String filterName;

    /**
     * Default constructor setting {@link String} as a filterClass
     */
    EmailMessageFilter() {
	this(String.class, null);
    }

    /**
     * Constructor set filterClass
     * 
     * @param filterClass
     *            to set
     */
    EmailMessageFilter(Class filterClass) {
	this(filterClass, null);
    }

    /**
     * Constructor set filterClass
     * 
     * @param filterClass
     *            to set
     */
    EmailMessageFilter(Class filterClass, String filterName) {
	this.filterClass = filterClass;
	this.filterName = filterName != null ? filterName : this.name();
    }

    /**
     * @return the filterClass
     */
    public Class getFilterClass() {
	return filterClass;
    }

    /**
     * @return the filterName
     */
    public String getFilterName() {
	return filterName;
    }

}
