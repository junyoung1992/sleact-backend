package cj.task.sleact.common.constants;

public class ApiUrlConstants {

    private static final String URL_API = "/api";

    public static class User {
        public static final String BASE_URL = URL_API + "/users";

        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
    }

    public static class Workspace {
        public static final String BASE_URL = URL_API + "/workspaces";

        public static final String WORKSPACE_MEMBERS = "/{workspace}/members";

        public static final String CHANNELS = "/{workspace}/channels";
        public static final String A_CHANNEL = "/{workspace}/channels/{channel}";
        public static final String CHANNEL_MEMBERS = "/{workspace}/channels/{channel}/members";

        public static final String CHAT = "/{workspace}/channels/{channel}/chats";
        public static final String IMAGE = "/{workspace}/channels/{channel}/images";
        public static final String UNREAD = "/{workspace}/channels/{channel}/unreads";
    }

}
