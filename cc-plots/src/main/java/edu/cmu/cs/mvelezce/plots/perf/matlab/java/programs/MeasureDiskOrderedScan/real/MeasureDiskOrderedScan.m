train = readtable('../../../../../../../../../../../../../../../cc-perf-model-learning/src/main/resources/eval/java/programs/MeasureDiskOrderedScan/real/MeasureDiskOrderedScan.csv');
times = table2array(train(:,17:17));
times = sort(times);

count = [];
for i=1:length(times)
    count = [count; i];
end

plot(count,times,'k','LineWidth',3);

title('Berkeley DB', 'Fontsize',80);
xlabel('Configurations');
ylabel('Performance (s)');

ylim([4.0, 28.0]);

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
saveas(gcf,'MeasureDiskOrderedScan',filetype)