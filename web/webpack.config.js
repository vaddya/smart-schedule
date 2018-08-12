const path = require('path')

module.exports = {
    mode: 'development',

    entry: './src/app.js',

    output: {
        path: path.join(__dirname, 'dist'),
        filename: 'app.js'
    },

    devtool: 'source-map',

    resolve: {
        extensions: ['.js', '.jsx'],
        alias: {
            components: path.resolve(__dirname, './src/components'),
            actions: path.resolve(__dirname, './src/actions'),
            reducers: path.resolve(__dirname, './src/reducers'),
            services: path.resolve(__dirname, './src/services'),
            store: path.resolve(__dirname, './src/store')
        }
    },

    module: {
        rules: [
            {
                test: /\.jsx?$/,
                include: /src/,
                loader: 'babel-loader',
                query: {
                    presets: ['env', 'react', 'stage-2']
                }
            },
            {
                test: /\.sass$/,
                include: /src/,
                loader: 'style-loader!css-loader!sass-loader'
            },
            {
                test: /\.css$/,
                include: /src/,
                loader: 'style-loader!css-loader'
            }
        ]
    },

    devServer: {
        historyApiFallback: true,
        port: 9000
    }
};

