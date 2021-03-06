/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flink;

import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	static final StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
	static final EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
	static final StreamTableEnvironment tEnv = StreamTableEnvironment.create(bsEnv, bsSettings);

	public static void main(String[] args) throws Exception {
		// Checking input parameters
		final MultipleParameterTool params = MultipleParameterTool.fromArgs(args);

		// set up the execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		final String SQL_SCRIPT_DIR = System.getProperty("user.dir") + "/build-target/sql-scripts/";

		// make parameters available in the web interface
		env.getConfig().setGlobalJobParameters(params);

		if (!params.has("sql-script")) {
			System.out.println("Executing sql script jar with default input data set.");
			System.out.println("Use --sql-script to specify file input.");
			return;
		}

		LinkedList<String> queries = new LinkedList<>();
		String sqlScript = params.get("sql-script");
		Files.lines(Paths.get(SQL_SCRIPT_DIR + sqlScript)).forEachOrdered(line -> {
			queries.add(line);
		});

		System.out.println("Starting to execute sql statement from " + sqlScript + " file.");

		for (String query : queries) {
			TableResult result = tEnv.executeSql(query);
		}

		env.execute("Streaming Job");
	}
}
