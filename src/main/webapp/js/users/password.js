define(['sha256'], function () {
    return {
        hash: function (password) {
            return sha256(password);
        }
    };
});
