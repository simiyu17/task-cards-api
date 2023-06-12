package com.card.task.api;

public enum SwaggerDocumentationConstant {
    SWAGGER_DOCUMENTATION_CONSTANT;

    public static final String CARDS_SEARCH_AND_FILTER_DESC = """
                    Search for task cards. All available filters are optional.
                    All filters here work in 'and' version, this means that for
                    example if you provide cardName, dateCreated, fromDateCreated and toDateCreated
                    for filtering, the cards returned will contain cardName in their name and
                    will have been created on dateCreated and also will have been created
                    between fromDateCreated and toDateCreated and not deleted and user is ADMIN
                    or cards were created by the user.
                    """;
    public static final String CARD_DELETE_DESC = """
                    Provide the database primary key to delete a card (Mark it as deleted).
                    If a card is found,
                    the user must be an ADMIN user or card owner (User who created the card).
                    Otherwise the response will be the same as a card is not found response.
                    """;
    public static final String FIND_CARD_BY_ID_DESC = """
                    If a card is found,
                    the user must be an ADMIN user or card owner (User who created the card).
                    Otherwise the response will be the same as a card is not found response.
                    """;
    public static final String UPDATE_CARD_BY_ID = """
                    If a card is found,
                    the user must be an ADMIN user or card owner (User who created the card).
                    Otherwise the response will be the same as a card is not found response.
                    A user can update the name, the description, the color and/or the status of a card.
                    Contents of the description and color fields can be cleared out.
                    Color, if provided, should conform to a “6 alphanumeric characters prefixed with a #“ format.
                    Available statuses are TODO/IN_PROGRESS/DONE.
                    """;
    public static final String CREATE_CARD = """
                    Name is mandatory.
                    A description and a color are optional.
                    Color, if provided, should conform to a “6 alphanumeric characters prefixed with a #“ format.
                    Upon creation, the status of a card is To Do.
                    """;
}
