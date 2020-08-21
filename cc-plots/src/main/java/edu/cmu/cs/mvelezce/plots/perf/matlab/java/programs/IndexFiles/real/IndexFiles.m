train = readtable('../../../../../../../../../../../../../../../cc-perf-model-learning/src/main/resources/eval/java/programs/IndexFiles/real/IndexFiles.csv');
times = table2array(train(:,18:18));
times = sort(times);

count = [];
for i=1:length(times)
    count = [count; i];
end

plot(count,times,'k','LineWidth',3);

title('Apache Lucene', 'Fontsize',80);
xlabel('Configurations');
ylabel('Performance (s)');

ylim([23.0, 36.0]);

set(gca,'FontSize',20);
set(gca,'xtick',[])

set(gcf,'Position',[100 100 400 300])

scale=2;
paperunits='centimeters';
filewidth=7.5;%cm
fileheight=5.5;%cm
filetype='pdf';
res=300;%resolution
size=[filewidth fileheight]*scale;
set(gcf,'paperunits',paperunits,'paperposition',[0 0 size]);
set(gcf, 'PaperSize', size);
saveas(gcf,'IndexFiles',filetype)