base joern 2.0.404

```shell
# 生成 cpg
./javasrc2cpg -J-Xmx2048m /Users/ppp/Documents/pppRepository/github_file/JavaRce/SecVulns --output ../source/SecVulns.cpg.bin

# 执行脚本
./joern --script /Users/ppp/Documents/pppRepository/github_file/JavaRce/joern/joern.sc --param cpgFile=../source/SecVulns.cpg.bin --param outFile=../source/output.log
```