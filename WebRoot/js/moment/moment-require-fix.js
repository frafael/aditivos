(function (moment, require, undefined) {
    // Fix an issue with moment using require.js in the language files
    window.require = function (key) {
        if (key == "../moment")
            return moment;
        if (require != undefined)
            return require.apply(this, arguments);
        return null;
    };
})(moment, window.require);