# flink-sql-jar

该项目的 sql jar 包项目参考 https://ci.apache.org/projects/flink/flink-docs-release-1.12/dev/project-configuration.html 搭建，并按照文档添加了所需的 dependency https://ci.apache.org/projects/flink/flink-docs-release-1.12/dev/table/.

如果需要添加额外逻辑，可以在 sql jar package 中的 streaming java 文件添加额外逻辑。

本项目的 docker compose 文件包含 sql editor 的样例配套设置, 可以通过 docker-compose up 来启动，并通过端口 8888 来访问 sql editor。该集群包含 kafka, zookeeper, hue, mysql, flink sql gateway, flink cluster 等集群。
