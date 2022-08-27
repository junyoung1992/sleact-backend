package cj.task.sleact.common.constants;

public class ApiUrlConstants {

    private static final String URL_API = "/api";

    public static class Workspace {
        public static final String BASE_URL = URL_API + "/workspaces";

        public static final String CHANNELS = "/{workspace}/channels";
        public static final String A_CHANNEL = "/{workspace}/channels/{channel}";
    }

}
