package io.appium.uiautomator2.http;

import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    private final int port;
    private final List<IHttpServlet> handlers = new ArrayList<IHttpServlet>();
    private Thread serverThread;

    public HttpServer(int port) {
        this.port = port;
    }

    public void addHandler(IHttpServlet handler) {
        handlers.add(handler);
    }

    public void start() {
        if (serverThread != null) {
            throw new IllegalStateException("Server is already running");
        }
        serverThread = new Thread() {
            @Override
            public void run() {
                EventLoopGroup bossGroup = new NioEventLoopGroup(1);
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
                    bootstrap.option(ChannelOption.SO_REUSEADDR, true);
                    bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ServerInitializer(handlers));

                    Channel ch = bootstrap.bind(port).sync().channel();
                    ch.closeFuture().sync();
                } catch (InterruptedException ignored) {
                } finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            }
        };
        serverThread.start();
    }

    public void stop() {
        if (serverThread == null) {
            throw new IllegalStateException("Server is not running");
        }
        serverThread.interrupt();
    }

    public int getPort() {
        return port;
    }
}
