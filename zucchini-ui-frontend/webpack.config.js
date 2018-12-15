/* eslint-disable */

const path = require("path");
const webpack = require("webpack");
const TerserPlugin = require('terser-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");
const LodashModuleReplacementPlugin = require('lodash-webpack-plugin');

// Output dir
const outputDir = path.join(__dirname, "build/dist/assets");

// Config
const config = require("./config.json");

module.exports = {
  entry: {
    main: "./src/main.js"
  },
  resolve: {
    modules: [path.join(__dirname, "src"), "node_modules"],
    extensions: [".js", ".jsx", ".less", ".css"]
  },
  output: {
    path: outputDir,
    filename: "[name].js",
    publicPath: "/ui/assets/"
  },
  devtool: "source-map",
  devServer: {
    port: config.devServer.port,
    before: function(app) {
      app.get("/ui/assets/config.js", (req, res) => {
        // Connect function to serve a Javascript configuration file
        res.writeHead(200, { "Content-Type": "application/javascript" });
        res.end(`var configuration = ${JSON.stringify(config.ui)};`);
      });
    }
  },
  externals: {
    configuration: "configuration"
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        enforce: "pre",
        use: ["eslint-loader"]
      },
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        use: ["babel-loader?cacheDirectory"]
      },
      {
        test: /\.css$/,
        use: [MiniCssExtractPlugin.loader, "css-loader?sourceMap", "postcss-loader?sourceMap"]
      },
      {
        test: /\.less$/,
        use: [MiniCssExtractPlugin.loader, "css-loader?sourceMap", "postcss-loader?sourceMap", "less-loader?sourceMap"]
      },
      {
        test: /\.(ttf|eot|woff2?|svg|png|jpg|gif)$/,
        use: ["file-loader"]
      }
    ]
  },
  optimization: {
    minimizer: [
      new TerserPlugin(),
      new OptimizeCSSAssetsPlugin({})
    ],
    splitChunks: {
      chunks: "all"
    }
  },
  plugins: [
    new MiniCssExtractPlugin(),

    // Replace lodash-es imports by equivalent lodash imports.
    // Otherwise, same lodash functions can be loaded twice !
    new webpack.NormalModuleReplacementPlugin(/lodash-es/, resource => {
      resource.request = resource.request.replace("lodash-es", "lodash");
    }),

    new LodashModuleReplacementPlugin()
  ]
};
