const express = require('express')

module.exports = {
    devServer: {
        onBeforeSetupMiddleware: function (devServer) {
            devServer.app.use((req, res, next) => {
                const now = new Date().toISOString()
                console.log(`[${now}] ${req.method} ${req.url}`)
                next()
            })
        },
    },
}
