x = [5, 20, 100, 80, 30];
y = [100, 90, 1, 2, 4];
n = ["PW", "FW", "BF/SA", "SAD", "CC"];

scatter(x,y,1500,'.');
text(x, y, n, 'horizontal','left', 'vertical','bottom')
xlabel('Cost [Configurations]');
ylabel('Prediction Error [MAPE]');
title('Performance modeling approaches');
xlim([0 105]);
ylim([-2 105]);
xticks(linspace(0,100,0));
yticks(linspace(0,100,0));
fontset;
 
fig = gcf;
fig.PaperPositionMode = 'auto';
fig_pos = fig.PaperPosition;
fig.PaperSize = [fig_pos(3) fig_pos(4)];

mkdir('../../../../../../../../resources/evaluation/programs/java/concept/plots/');
fileID = '../../../../../../../../resources/evaluation/programs/java/concept/plots/prediction_error.pdf';
print(fig, fileID,'-dpdf');