train = readtable('../../../../../../../../../../../../../../../cc-perf-model-learning/src/main/resources/eval/java/programs/Convert/user/Convert.csv');
times = table2array(train(:,23:23));
times = sort(times);

count = [];
for i=1:length(times)
    count = [count; i];
end

plot(count,times,'k','LineWidth',3);

title('Density Converter', 'Fontsize',80);
xlabel('Configurations');
ylabel('Performance (s)');

ylim([2.0, 201.0]);

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
saveas(gcf,'Convert',filetype)