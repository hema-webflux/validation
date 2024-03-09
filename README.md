# Validation

```java

import java.util.HashMap;
import java.util.Map;

protected Map<String, Object> messages() {

    Map<String, Object> messages = new HashMap<>();

    // 通配符匹配由email开头的字段
    messages.put("email*", ":attribute 不合法");

    // 或者为email开头的字段的rule定义消息

    Map<String, String> emailMessages = new HashMap<>();
    emailMessages.put("required", ":attribute 不能为空");
    emailMessages.put("email", ":attribute 不合法");
    
    messages.put("email*", emailMessages);

    return messages;
}

```