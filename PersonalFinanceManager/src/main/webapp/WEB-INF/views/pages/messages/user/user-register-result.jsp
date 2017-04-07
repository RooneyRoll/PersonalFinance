<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="message-container">
    <div class="message-content no-select">
        <div class="message-title">
            <span class="message-title-icon">
                <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
                <span class="message-title-text">
                    ${messageTitle}
                </span>
        </div>
        <div class="message-text-holder">
            <span>${messageText}</span>
        </div>
    </div>
</div>